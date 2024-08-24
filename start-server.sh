# start-server.sh

#docker pull \$IMAGE_TAG &&
#docker run -d -p 80:80 \$IMAGE_TAG

# .env 파일에서 환경 변수 읽어오기
if [[ -f .env ]]; then
  echo "Loading environment variables from .env file"
  source .env
  export DB_URL=$DB_URL
  export DB_USERNAME=$DB_USERNAME
  export DB_PASSWORD=$DB_PASSWORD
  export GCP_IMAGE=$GCP_IMAGE
else
  echo "Error: .env file not found"
  exit 1
fi

# 빌드 후 도커 컨테이너 실행
#./gradlew clean build &&  # 1. build & unit test
#docker compose -f docker-compose.local.yml down &&  # 2. 기존 컨테이너 종료 및 삭제
#docker compose -f docker-compose.local.yml build --no-cache &&  # 3. 새로운 이미지 빌드
#docker compose -f docker-compose.local.yml up -d  # 4. 컨테이너 실행