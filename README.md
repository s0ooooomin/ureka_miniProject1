# ureka_miniProject1
# dvdvdeep : 영화dvd 대여관리시스템
JAVA
DB : MySQL
GUI : Swing
 
1 : 메뉴

2-1 : 대여목록 : order_id, movie_name, cust_name, cust_phone, borrowdate
- 반납
order_id : 현재 선택된 열의 order_id
→ UPDATE returndate , saleprice
→ insert Sale + delete Order
→ saleprice 출력 + 결제:commit 취소:rollback 
- 반납 시 금액 관리
- 날짜 수정 (구현X), 삭제

2-2 : 영화목록 : movie_id, movie_name, director, price
- 영화 검색 ( searchWord )
- 대여
name, phone, moviename, borrowdate 입력
→ phone 존재시 insert Order
phone 미존재시 insert Customer + insert Order
- 추가, 가격 수정, 삭제

2-3 : 고객목록 : cust_id, cust_name, cust_phone
- 추가, 전화번호 수정, 삭제
  
2-4 : 판매목록 : sale_id, movie_id, saleprice, returndate
- 판매액 총합(구현X)
