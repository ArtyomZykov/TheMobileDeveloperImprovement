<details close>
<summary>Исзодный план от ментора (Оригинал https://docs.google.com/document/d/1GjZxxSaG780reKt4n9e-4H1N9C5qCx1M31UTA3GEKrY)</summary>
<br>
Цели: 

1. Подтянуть нативную разработку под Android
2. Углубить и актуализировать знания языка Kotlin 
3. Углубить и актуализировать знание KMP. Освоить современный стек разработки
4. Базовые знания по iOS


Android
- Основные компоненты Android приложения. Activity, Fragment, layouts. Activity Launch Mode
- Основные компоненты Android приложения. Фоновая работа. Background service. Work Manager
- Основные компоненты Android приложения. Broadcast Receiver
- Основные компоненты Android приложения. ContentProvider
- Jetpack библиотеки для современного Android приложения. ViewModel
- SharedPreferences, DataStore
1. Точка контроля: написать простое приложение Android с шедулированием работы в фоне. Предусмотреть корректность работы по жизненному циклу ViewModel и Activity

- Архитектура современного Android приложения. MVVM, CleanArch. UseCase
- DI на Dagger, Hilt
2. Точка контроля: написать приложение Android с 1-2 Dagger модулями. Использовать архитектуру CleanArch с UseCase
  
- Сетевой слой на Retrofit + OkHttp. Gson
- Хранилища данных в Android. Локальное хранилище Room. 
3. Точка контроля: написать приложение Android с сетевым клиентом. Добавить сохранение закешированных данных с помощью Room и SharePreferences.


Compose
- Архитектура Compose приложения
- Управление состоянием приложения, создание и конфигурация модулей, сохранение состояния ViewModel.
- Навигация в Compose приложении
4. Точка контроля: написать приложение Android Compose на 2-3 экрана. Выбрать архитектуру, выбор обосновать. Предусмотреть корректность управления состоянием. Адаптировать бизнес-логику к архитектуре Compose.


Kotlin Multiplatform. Современный стек

- Современное состояние Kotlin Native, управление памятью и современная таблица Interop
- Современное состояние многопоточности. Общие Dispatchers. Coroutines
- Сетевой слой на Ktor
- Локальное хранилище на Room
- Использование DI Koin/Kodein
- Приложение с общей архитектурой, общей ViewModel.
- Управление состоянием приложения, создание и конфигурация модулей, сохранение состояния ViewModel.
- Compose Multiplatform. 
- Навигация (PreCompose, Decompose)
5. Точка контроля: 
  5.1 адаптировать приложение Android под приложение KMP
  5.2 Реализовать приложение KMP на современном стеке


Kotlin
- Обычные классы, Data классы, Sealed
- Модификаторы доступа
- Unit, Notning, Any
- Sealed vs enum
- inline классы, generics
- interface vs Abstract классы, Sealed interface
- Extensions
- Kotlin Flows, Shared vs State
- Виды ссылок, Strong, Weak
</details>


<details close>
<summary>Точки контроля 1-3. weather-tracker</summary>
<br>
Было прнято решение объеденить 3 точки контроя в одно прилодение. Weather Tracker позволяет пользователям получать текущую информацию о погоде и прогноз на несколько дней вперед для выбранного города. Приложение также поддерживает фоновую работу для обновления данных о погоде и уведомления о значительных изменениях.

https://github.com/ArtyomZykov/TheMobileDeveloperImprovement/tree/master/weather-tracker

Стек:
1. Activity: MainActivity с Launch Mode singleTask
2. Fragments
3. XML layouts
4. Jetpack ViewModel
5. Retrofit + OkHttp: для получения данных о погоде из открытого API.
6. Room: для локального сохранения данных о погоде.
7. WorkManager: для фонового обновления данных о погоде.
8. Broadcast Receiver: для уведомлений о значительных изменениях погоды.
9. ContentProvider: для предоставления данных о погоде другим приложениям.
</details>
