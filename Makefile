MAKEFLAGS += --silent

.PHONY: build
build:
	./mvnw package

.PHONY: test
test:
	./mvnw test

.PHONY: clean
clean:
	./mvnw clean

.PHONY: docker-build
docker-build:
	./docker-build.sh

.PHONY: docker-run
docker-run:
	./docker-run.sh

.PHONY: parse-portfolio
parse-portfolio:
	curl -s localhost:8080/parse -XPOST -F "funds=@tmp/2018-02-19T2123/funds.html" -F "cash=@tmp/2018-02-19T2123/patrimonio.html" | jq "." > tmp/2018-02-19T2123/portfolio.json
	cat tmp/2018-02-19T2123/portfolio.json | jq "."

.PHONY: 2fa
2fa:
	echo "input your OTP code"
	curl -s localhost:8080/2fa -XPOST -H "Content-Type: application/json" -d '{"otp":"691123"}'

.PHONY: login
login:
	curl -s localhost:8080/login -XPOST -H "Content-Type: application/json" -d '{"username":"AzureDiamond", "password": "hunter2", "nif": "Cthon98"}'

.PHONY: make-operations
make-operations:
	curl -s localhost:8080/operations -XPOST -H "Content-Type: application/json" --data-binary @/tmp/rebalance_orders.json
