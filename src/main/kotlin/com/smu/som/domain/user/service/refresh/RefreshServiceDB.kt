package com.smu.som.domain.user.service.refresh

import com.smu.som.common.util.Constants.Companion.REFRESH_TOKEN_PREFIX
import com.smu.som.controller.error.BusinessException
import com.smu.som.controller.error.ErrorCode
import com.smu.som.domain.user.dto.JwtTokenDTO
import com.smu.som.domain.user.entity.RefreshToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
@Transactional(readOnly = true)
class RefreshServiceDB(
	private val refreshRepository: RefreshTokenRepository
) : RefreshService {

	@Value("\${jwt.refresh-token-expiry}")
	private val refreshTokenExpiry: Long = 0

	//jwt token 정보를 받고 이를 refresh token으로 해당 id에 저장합니다
	@Transactional
	override fun storeRefresh(jwtToken: JwtTokenDTO, oauth2Id: String) {
		refreshRepository.deleteByOauth2Id(oauth2Id)
		refreshRepository.save(
			RefreshToken(
				refreshToken = "$REFRESH_TOKEN_PREFIX:${jwtToken.refreshToken}",
				oauth2Id = oauth2Id
			)
		)
	}

	//해당 refresh token을 검색하여 return 합니다
	//중복된 token일 경우 기존 token을 삭제합니다
	@Transactional
	override fun getRefresh(refreshToken: String): String? {
		val findRefreshToken = refreshRepository.findByRefreshToken(refreshToken)
			?: return null

		if (!isValidate(findRefreshToken)) {
			refreshRepository.delete(findRefreshToken)
			return null
		}
		return findRefreshToken.oauth2Id
	}

	//해당 refresh token을 삭제합니다
	override fun deleteRefresh(refreshToken: String) {
		val findRefreshToken = refreshRepository.findByRefreshToken(refreshToken)
			?: throw BusinessException(ErrorCode.INVALID_REFRESH_TOKEN)

		refreshRepository.delete(findRefreshToken)
	}

	//해당 token 정보가 중복되었는지에 대한 검사를 진행합니다
	private fun isValidate(refreshToken: RefreshToken): Boolean {
		val expiryDateTime = refreshToken.createdAt.plus(refreshTokenExpiry, ChronoUnit.MILLIS)
		if (LocalDateTime.now().isAfter(expiryDateTime)) {
			return false
		}
		return true
	}
}
