
# CoinMarket App

CoinMarket is an Android application for tracking cryptocurrency data. It provides live price updates, 24-hour change percentages, market cap, trading volume, price charts, and detailed information about each cryptocurrency.

---

## Features

- Display a list of cryptocurrencies with name, price, market cap, and 24-hour change.
- View interactive price charts with selectable time periods:
  - 1 hour, 24 hours, 1 week, 1 month, 3 months, 1 year, all-time.
- View detailed statistics for each cryptocurrency:
  - Open price, high, low, 24-hour change, algorithm, total volume, supply, market cap.
- View additional information:
  - Website, Reddit, Twitter, GitHub, and description.
- Interactive price charts with color-coded gains/losses.
- Direct links to official websites and social media.

---

## Screenshots

![Screenshot 1](path/to/screenshot1.png)
![Screenshot 2](path/to/screenshot2.png)

---

## Demo GIF

![Demo GIF](path/to/demo.gif)

---

## Screens

1. **MarketActivity**  
   - Displays a list of cryptocurrencies.
   - Uses `RecyclerView` with `Market_Adapter`.
   - Shows coin images with Glide.

2. **CoinActivity**  
   - Displays details of a selected cryptocurrency.
   - Uses `SparkView` for price chart with `Chart_Adapter`.
   - Shows statistics and detailed information.
   - Supports clickable links to website, Reddit, Twitter, and GitHub.
---
Libraries Used

Retrofit - for API calls

Gson Converter - for JSON parsing

Glide - for image loading

Robinhood Spark - for charts

AndroidX components (RecyclerView, ConstraintLayout, AppCompat, etc.)

