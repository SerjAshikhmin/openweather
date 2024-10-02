Необходимо Реализовать демо-приложение для отображения погоды.
Можно использовать любой удобный публичный API (например Open Weather Map).
### Краткое описание задания:
Приложение должно иметь функциональность:
1. Отображение погоды в выбранном городе.
2. Поиск других городов.
3. Сохранение города в избранное.
### Подробное описание задания:
Приложение должно представлять из себя Single Activity, у которого есть 3 фрагмента:
- фрагмент для отображения погоды в конкретном городе (Фрагмент_1)
- фрагмент для поиска других городов (Фрагмент_2)
- фрагмент для отображения избранных городов (Фрагмент_3)
  Обязательные требования:
- Фрагмент_1 может быть классическим фрагментом, остальные фрагменты могут быть реализованы через BottomSheetDialog.
- Activity при запуске сразу должна отобразить Фрагмент_1 с городом, определённым по геолокации. Реализовать правильную обработку пермишенов на геолокацию.
- Фрагмент_1 должен отображать название города, текущую погоду (температура и ещё по крайней мере 2 параметра на выбор), изображение (по крайней мере в 3х вариантах - облако, солнце, дождь).
- В верхнем правом углу Фрагмент_1 должна быть кнопка, позволяющая открыть экран поиска (Фрагмент_2).
- В нижней части Фрагмент_1 должна быть возможность добавить город в избранное (звёздочка в двух состояниях - добавлено/не добавлено).
- Справа от "звёздочки" должна быть кнопка "избранные города", позволяющая перейти на экран всех избранных городов (Фрагмент_3).
- Фрагмент_2, реализующий функциональность поиска, должен иметь строку поиска, список найденных городов, а также давать возможность добавить город в избранное ("звёздочка" справа от каждого города) и перейти в город по нажатию на него.
- Фрагмент_3, реализующий функциональность отображения избранных городов, должен давать возможность перейти на выбранный город а также возможность удалить город из избранного.
- В момент загрузки каких либо данных на всех трёх экранах должен отображаться лоадер.
- Кешироваться должны данные о погоде в ранее открытом городе, а также информация об избранных городах.
- Экран просмотра погоды в конкретном городе должен сначала отобразить информацию из кеша, а потом догрузить с API.
- При отсутствии соединения должна быть возможность просмотреть погоду в текущем городе (если она есть в кеше), и погоду в избранных городах.
  Технический стек:
1. Код должен быть написан на Kotlin.
2. Презентативный слой должен быть реализован на MVVM.
3. Зависимости должны предоставляться через dagger.
4. Для кеша использовать Room.
5. Для многопоточности - rxjava/coroutines.
6. Для работы с API - retrofit.
7. Для JSON - gson.
8. Если понадобится отображать картинки - Glide.
   Решение загрузить на github и скинуть ссылку.
   По результатам review будет техническое обсуждение предоставленной реализации.