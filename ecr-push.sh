aws ecr-public get-login-password --region us-east-1 --profile jenga |
docker login --username AWS --password-stdin public.ecr.aws/f9s3i1t6

docker build --platform amd64 -t jenga .

docker tag jenga:latest public.ecr.aws/f9s3i1t6/jenga:latest

docker push public.ecr.aws/f9s3i1t6/jenga:latest
