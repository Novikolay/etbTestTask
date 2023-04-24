## Тестовое задание ЭнергоТрансБанк

----
Необходимо реализовать:
- Таблица клиентов(ID + наименование)
- Таблица со счетами клиентов(номер счета + остаток)
- Реализовать просмотр списка клиентов и его счетов.
- Реализовать простую проводку между счетами клиентов – просто дебет-кредит суммы с изменением остатка на счете.
- Реализовать выгрузку импорт-экспорт списка клиентов со счетами в XML.
- Реализовать историю операций проводок по счетам клиентов  
Язык Java или C#  
БД - на усмотрение  

UPD: необходима возможность осуществлять операции со стороны фронта

----

----

## Информация для конфигурации и развертывания

### Зависимости
- Java 11
- Spring Boot
- Postgresql
- Swagger
- Thymeleaf

### Сборка
```bash
mvn clean package
```

### Сборка + запуск
```bash
mvn spring-boot:run
```

### БД
Postgresql

### Swagger для отладки
http://localhost:8888/swagger-ui.html

### UI
http://localhost:8888/

### API
Для вызова доступны точки:

```/actions/all```  
Возвращает историю операций по счетам

```/bills/all```  
Возвращает все счета из БД

```/bills/id```  
Возвращает конкретный счет из БД по id

```/bills/calcDebit```  
Рассчет дебета суммы, где Кредитный счет - | Дебетовый +  
Принимает на вход Id кредитного счета, Id дебетного счета и сумма кредита.

```/bills/calcCredit```  
Рассчет дебета суммы, где Кредитный счет - | Дебетовый +  
Принимает на вход Id кредитного счета, Id дебетного счета и сумма кредита.

```/customers/all```  
Возвращает всех клиентов из БД

```/customers/id```  
Возвращает конкретного клиента из БД по id

```/customers/exportCustomerList```  
Экспорт списка клиентов.  
Возвращает XML дамп БД клиентов и их счетов в указанном ниже формате.

```/customers/importCustomerList```  
Импорт списка клиентов.  
Принимает на вход XML дамп БД клиентов и их счетов в указанном ниже формате.  
Уже существующие данные записи обновляются, новые - добавляются.

### UI Страницы
* http://localhost:8080/info       — главная (клиенты + их счета)
* http://localhost:8080/bills      — страница просмотра Счетов
* http://localhost:8080/customers  — страница просмотра Клиентов
* http://localhost:8080/operations  — страница осуществления операций со счетами
* http://localhost:8080/xml  — страница ипорта/экспорта XML
* http://localhost:8080/history  — страница просмотра истории операций со счетами

### Формат XML для импорта/экспорта
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<CustomerData>
    <CustomerList>
        <Id>1</Id>
        <Name>ОАО ЖилСтройСервис</Name>
        <BillList>
            <Id>4</Id>
            <Amount>389</Amount>
        </BillList>
    </CustomerList>
    <CustomerList>
        <Id>2</Id>
        <Name>ООО Ромашка</Name>
        <BillList>
            <Id>2</Id>
            <Amount>0</Amount>
        </BillList>
        <BillList>
            <Id>6</Id>
            <Amount>3000</Amount>
        </BillList>
        <BillList>
            <Id>1</Id>
            <Amount>20412</Amount>
        </BillList>
    </CustomerList>
    <CustomerList>
        <Id>3</Id>
        <Name>ИП Вася Пупкин</Name>
        <BillList>
            <Id>3</Id>
            <Amount>400</Amount>
        </BillList>
        <BillList>
            <Id>5</Id>
            <Amount>1400</Amount>
        </BillList>
    </CustomerList>
</CustomerData>
```

### Текущие ошибки
- Испорт/экспорт XML работает только в swagger 
