# bank-system-java
Console based bank system
# Bank System — Java

Консольная банковская система: управление клиентами, счетами и транзакциями.  
Язык: **Java (Core)** | Интерфейс: **CLI** | Данные: **in-memory (List + HashMap)**

---

## Структура проекта

```
src/
├── model/
│   ├── Client.java         # Клиент банка
│   ├── Account.java        # Банковский счёт
│   └── Transaction.java    # Запись о транзакции
├── service/
│   └── BankService.java    # Вся бизнес-логика и аналитика
└── main/
    └── Main.java           # Консольное меню (точка входа)
```

---

## Как запустить

```bash
cd src
javac model/Client.java model/Account.java model/Transaction.java service/BankService.java main/Main.java
java main.Main
```

### Через IntelliJ IDEA
1. File → Open → папка `bank-system`
2. Правой кнопкой на `src` → Mark Directory as → Sources Root
3. Открыть `main/Main.java` → Run

---

## Возможности

| Пункт меню | Описание |
|---|---|
| 1. Add client | Зарегистрировать клиента |
| 2. Open account | Открыть счёт (DEBIT / CREDIT), номер генерируется автоматически |
| 3. Deposit | Пополнить счёт |
| 4. Withdraw | Снять деньги |
| 5. Transfer | Перевод между счетами |
| 6. Show balance | Текущий баланс счёта |
| 7. Transaction history | История операций по счёту |
| 8. Analytics | Аналитика (см. ниже) |
| 0. Exit | Выход |

### Аналитика (пункт 8)

| Пункт | Описание |
|---|---|
| 1. Total balance of client | Суммарный баланс всех счетов клиента |
| 2. Clients above threshold | Клиенты с суммарным балансом выше порога |
| 3. Richest client | Клиент с наибольшим суммарным балансом |

---

## Валидация

- Нельзя снять больше, чем есть на счёте
- Нельзя вносить / снимать нулевую или отрицательную сумму
- Тип счёта только DEBIT или CREDIT
- Номер счёта генерируется автоматически: ACC-1001, ACC-1002, ...
- Несуществующий счёт или клиент — сообщение об ошибке, не падает
