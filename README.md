# UI Automation Project (Selenium + JUnit 5)

[![CI](https://github.com/AndreiDovidovich/selenium-junit-project/actions/workflows/ci.yml/badge.svg)](https://github.com/AndreiDovidovich/selenium-junit-project/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-21-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.49.0-green)
![JUnit](https://img.shields.io/badge/JUnit-5.11.3-red)
![Allure](https://img.shields.io/badge/Allure-2.35.5-yellow)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven)
![WebDriverManager](https://img.shields.io/badge/WebDriverManager-6.3.4-informational)
![Log4j2](https://img.shields.io/badge/Log4j2-2.26.1-orange)

UI-автотесты для [demoqa.com](https://demoqa.com) на Selenium + JUnit 5 + Allure.
Проект демонстрирует Page Object Model, Element Object, Allure-отчётность и GitHub Actions CI.

## Стек

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 21 | Язык |
| Selenium WebDriver | 4.x | Автоматизация браузера |
| JUnit 5 | 5.11 | Test runner |
| Maven | 3.9+ | Сборка |
| Allure Report | 2.29 | Отчётность |
| WebDriverManager | 5.9 | Управление драйверами |
| Log4j2 | 2.24 | Логирование |

## Покрытие

| Раздел | Тесты |
|--------|-------|
| **Login** | неверный пароль, неверный username |
| **Book Store — Search** | поиск по названию, по автору, по части названия, негативный поиск, data-driven |

## Архитектура

```
src/main/java/
├── elements/         # Обёртки над WebElement (InputElement, ButtonElement, TableElement, ...)
├── pages/            # Page Object — структура страниц
└── utils/            # LoadPropertiesUtils

src/test/java/
├── tests/            # Сами тесты
└── resources/        # config.properties, log4j2.xml
```

- **Elements** — типизированные обёртки над Selenium-элементами с логированием и Allure-шагами.
- **Pages** — описывают структуру страницы и бизнес-действия (`login`, `search`).
- **Tests** — только сценарии и проверки.


## Как запустить
```bash
mvn clean test

# Headless (как на CI)
mvn clean test -Dheadless=true

# Только smoke-тесты
mvn clean test -Dgroups=smoke
```

## Отчёт
```bash
mvn allure:serve
```
Откроется браузер с интерактивным отчётом: шаги, вложения, скриншоты упавших тестов, история прогонов.

## Пример Allure отчета
![Allure Report](allure-report.png)

## CI

GitHub Actions запускает тесты на каждый `push` и `pull_request` в `main`,
прогоняет их в headless-режиме и публикует Allure-отчёт на GitHub Pages.

Workflow: [`.github/workflows/ci.yml`](.github/workflows/ci.yml)

## Структура проекта

```
.
├── .github/
│   └── workflows/
│       └── ci.yml                    # GitHub Actions
├── src/
│   ├── main/java/
│   │   ├── elements/                 # Element Object
│   │   ├── pages/                    # Page Object
│   │   └── utils/                    # Утилиты
│   └── test/
│       ├── java/tests/               # Тесты
│       └── resources/
│           ├── config.properties     # Конфиг
│           └── log4j2.xml            # Логирование
├── pom.xml
└── README.md
```
