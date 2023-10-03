## 요구 사항

- 29cm의 상품 주문 프로그램을 작성합니다.
- 한 번에 여러개의 상품을 같이 주문할 수 있어야 합니다.
- 상품은 이름, 가격, 수량을 가지고 있습니다
- 상품번호, 주문수량은 반복적으로 입력 받을 수 있습니다.
- 주문은 상품번호, 수량을 입력받습니다.
    - empty 입력(space + ENTER)이 되었을 경우 해당 건에 대한 주문이 완료되고, 결제하는 것으로 판단합니다.
    - 결제 시 재고 확인을 하여야 하며 재고가 부족할 경우 결제를 시도하면 SoldOutException이 발생되어야 합니다.
- 주문 금액이 5만원 미만인 경우 배송료 2,500원이 추가되어야 합니다.
- 주문이 완료되었을 경우 주문 내역과 주문 금액, 결제 금액(배송비 포함)을 화면에 display 합니다.
- 'q' 또는 'quit'을 입력하면 프로그램이 종료되어야 합니다.
- Test 에서는 반드시 multi thread 요청으로 SoldOutException 이 정상 동작하는지 확인하는 단위테스트가 작성되어야 합니다.
- 상품의 데이터는 하단에 주어지는 데이터를 사용해주세요
    - 데이터를 불러오는 방식은 자유입니다.
    - 코드에 포함되어도 좋고, 파일을 불러도 되고, in memory db를 사용하셔도 됩니다.
    - 하지만 상품에 대한 상품번호, 상품명, 판매가격, 재고수량 데이터는 그대로 사용하셔야 합니다.
    - 상품 데이터 csv파일을 같이 제공합니다.


## 용어 정리

### 상품

| 한글명      | 영문명           | 설명                                      |
|----------|---------------|-----------------------------------------|
| 상품       | product       | 주문할 수 있는 상품                             |
| 상품 번호    | productNumber | 상품에 부여된 번호                       |
| 상품 가격    | price         | 상품의 가격                                  |
| 상품 이름    | name          | 상품의 이름                                  |
| 상품 재고 수량 | stock         | 상품의 재고 수량(주문수량이 재고 수량보다 을 시 주문 할 수 없다.) |

### 주문

| 한글명      | 영문명       | 설명                                    |
|----------|-----------|---------------------------------------|
| 주문       | order     | 상품을 주문                                |
| 주문 금액    | price     | 주문한 상품의 총 금액(5만원 미만시 배송비 2500원 추가 발생) |

### 주문 상품

| 한글명      | 영문명       | 설명        |
|----------|-----------|-----------|
| 주문 상품    | orderItem | 주문한 상품    |
| 주문 상품 수량 | quantity  | 주문한 상품의 수량 |

### 주문 상태

| 한글명   | 영문명   | 설명       |
|-------|-------|----------|
| 주문 시작 | START | 주문 시작 상태 |
| 주문 중  | ORDER | 주문 중 상태  |
| 주문 종료 | QUIT  | 주문 종료 상태 |

### 금액 설정

| 한글명         | 영문명   | 설명                        |
|-------------|-------|---------------------------|
| 무료 배달 최소 금액 | DELIVERY_FREE_ORDER_PRICE | 주문시 배달비 무료 최소 금액          |
| 배달 비        | DELIVERY_FREE_ORDER_PRICE | 무료 배달 최소 금액 이하 시 추가되는 배달비 |



## 프로젝트 구조
 - kr.co_29cm.homework: 주요 패키지로, 애플리케이션의 핵심 기능과 프로젝트 진입점인 OrderSystem 클래스가 포함되어 있습니다.
 - kr.co_29cm.homework.view: 사용자 인터페이스와 관련된 패키지로, 주문 시스템의 콘솔 뷰를 담당하는 클래스들이 있습니다.
 - kr.co_29cm.homework.domain: 주문, 주문 상품, 상품, 상품 정보 등 Entity와 Repository 관련 로직이 구현되어 있습니다.
 - kr.co_29cm.homework.exception: 예외 처리와 관련된 패키지로, SoldOutException과 같은 예외 클래스가 정의되어 있습니다.
 - kr.co_29cm.homework.application: 애플리케이션 로직과 관련된 패키지로, 상품 데이터를 CSV 파일에서 읽어와 데이터베이스에 저장하는 ProductService가 있습니다.

## 구현 방향
 - 상품 데이터를 CSV 파일에서 읽어와 데이터베이스에 저장합니다.
 - 도메인 모델 패턴 구현
 - 주문 시 상품 재고 확인을 Multi Thread로 처리하는 방식을 사용하여 성능을 향상시킵니다. CompletableFuture를 사용하여 병렬로 상품 재고를 확인하고, 결과를 종합하여 처리합니다.
 - 주문 시 상품 재고가 부족할 경우 SoldOutException이 발생합니다.
 - 주문 시 주문 금액이 5만원 미만인 경우 배송료 2,500원이 추가됩니다.
 - 주문이 완료되면 주문 내역과 주문 금액, 결제 금액(배송비 포함)을 화면에 출력합니다.
 - 주문이 완료되면 상품의 재고가 주문한 수량만큼 감소됩니다.
 - Test에서는 Multi Thread 요청으로 SoldOutException이 정상 동작하는지 확인하는 단위 테스트가 작성되어 있습니다.
