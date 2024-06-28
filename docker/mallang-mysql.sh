docker run -d \
--name mallang-mysql \
-e MYSQL_ROOT_PASSWORD="mallang" \
-e MYSQL_USER="mallang" \
-e MYSQL_PASSWORD="mallang" \
-e MYSQL_DATABASE="mallang" \
-p 3306:3306 \
mysql:latest