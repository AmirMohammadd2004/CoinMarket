# 🚀 CoinMarket App

[![Android](https://img.shields.io/badge/Platform-Android-green)](https://developer.android.com/) 
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-orange)](https://kotlinlang.org/) 
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

CoinMarket is a modern **Android application** for tracking cryptocurrency data in real-time. Stay updated with live prices, market trends, trading volumes, price charts, and detailed coin information — all in one place.

---

## 🌟 Features

- 📈 **Live Cryptocurrency List**  
  View name, price, market cap, and 24-hour change for all coins.

- 📊 **Interactive Price Charts**  
  Select time periods: 1h, 24h, 1w, 1m, 3m, 1y, All-time.  
  Color-coded gains/losses for easy analysis.

- 💹 **Detailed Coin Statistics**  
  Includes open price, high/low, 24-hour change, algorithm, total volume, supply, and market cap.

- 🌐 **Additional Coin Info**  
  Links to official website, Reddit, Twitter, GitHub, and detailed description.

- 🔗 **Direct Links & Navigation**  
  Open coin websites and social media directly from the app.

---

## 📸 Screenshots

![Screenshot 1](path/to/screenshot1.png)  
![Screenshot 2](path/to/screenshot2.png)  

---

## 🎬 Demo GIF

![Demo GIF](path/to/demo.gif)

---

## 🖥 Screens Overview

### 1. MarketActivity
- Displays a full list of cryptocurrencies.
- Uses `RecyclerView` with `Market_Adapter`.
- Shows coin images using **Glide**.

### 2. CoinActivity
- Displays detailed info of selected cryptocurrency.
- Interactive charts using **SparkView** with `Chart_Adapter`.
- Shows stats and additional links (website, Reddit, Twitter, GitHub).

---

## 🛠 Libraries Used

- **Retrofit** – For API calls  
- **Gson Converter** – For JSON parsing  
- **Glide** – For image loading  
- **Robinhood Spark** – For interactive charts  
- **AndroidX Components** – RecyclerView, ConstraintLayout, AppCompat, etc.

---

## ⚡ Installation

1. Clone the repository:  
```bash
git clone https://github.com/<username>/CoinMarket.git
