[![Build Status](https://travis-ci.org/Sekator778/Car_Sale.svg?branch=master)](https://travis-ci.org/Sekator778/Car_Sale)
[![codecov](https://codecov.io/gh/Sekator778/Car_Sale/branch/master/graph/badge.svg)](https://codecov.io/gh/Sekator778/Car_Sale)
# Car_Sale <tr>
  <p>platform for car sales</p>
### Used technologies
<hr>
<ul>
<li>Java 8</li>
<li>Maven</li>
<li>Hibernate</li>
<li>Liquibase</li>
<li>DBs: H2  for tests</li>
<li>DBs: Postgres  </li>
<li>log4j</li>
<li>JUnit</li>
<li>Servlet 4</li>
<li>Hibernate Validator Framework</li>
</ul>

### web приложение со следующими страницами.
1. основная страница. таблица со всеми объявлениям машин на продажу.<br>
Main page contains all ads for the sale of cars
<img src="https://github.com/Sekator778/Car_Sale/blob/master/src/main/resources/img/main.png" alt="main page with apply filter" width="900px" height="500px">
2. на странице есть кнопка. добавить новое объявление.<br>
3. переходить на страницу добавления.
<img src="https://github.com/Sekator778/Car_Sale/blob/master/src/main/resources/img/addform.png" alt="add car for sale" width="900px" height="400px">
4. есть категории машины. марка. тип кузова и т.д. .<br>
5. можно добавлять фото. для этого используется библиотека apache fileuppload
<img src="https://github.com/Sekator778/Car_Sale/blob/master/src/main/resources/img/cabinet.png" alt="user cabinet" width="900px" height="400px">
6. объявление имеет статус продано. или нет.<br>
7. в базе существуют пользователи. кто подал заявление. только он может менять статус.
также есть валидация пользователей
<img src="https://github.com/Sekator778/Car_Sale/blob/master/src/main/resources/img/check.png" alt="Check in" width="900px" height="150px">
<br>
<img src="https://github.com/Sekator778/Car_Sale/blob/master/src/main/resources/img/name.png" alt="Name exists into DB" width="900px" height="150px">



## В интерфейсе присутствует список с пунктами для фильтрации.
- показать за последний день
- показать с фото
- показать определенной марки.










