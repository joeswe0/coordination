# 상품 구매 코디 서비스
[Task 관리](./task.md)
## 기능 요구사항

프로젝트의 모든 API는 **HTTP**를 통해 **JSON** 형식으로 통신합니다.

--- 
1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

## 시스템
kotlin 1.9.25  
java 21 (adoptium/toolchain)  
gradle  
kotest

## 실행 방법

실행 후 아래 Swagger UI에서 확인 할 수 있습니다.
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

### 로컬 실행 
```bash
git clone https://github.com/joeswe0/coordination.git
cd coordination
./gradlew build
java -jar build/libs/*.jar
```

```bash
./gradlew :api:bootRun
```
