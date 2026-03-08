# 🕌 Prayer Times App

<p align="center">
  <img src="https://github.com/user-attachments/assets/32a53ed3-7457-4b3f-af64-cc9905aaebba" alt="Prayer Times Logo" width="200"/>
</p>

A modern Android application that displays daily prayer times for Cairo, Egypt with offline support and local notifications.

---

## 📱 Features

- **Prayer Times** — Fetches Fajr, Dhuhr, Asr, Maghrib, and Isha times from the Aladhan API
- **Next Prayer** — Automatically calculates and highlights the upcoming prayer
- **Countdown Timer** — Live countdown to the next prayer, updating every second with optimized recomposition
- **Offline Support** — Caches last fetched prayer times using Room Database, displayed when device is offline
- **Prayer Notification** — Triggers a local notification at the exact time of the next prayer, even in the background
- **Language Toggle** — Switch between Arabic and English UI instantly

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| DI | Hilt |
| Networking | Retrofit + Gson |
| Local Storage | Room Database |
| Notifications | AlarmManager + BroadcastReceiver |
| Async | Coroutines + StateFlow |

---

## 🏗️ Project Structure

```
app/
├── di/                      # Dependency Injection modules
├── notification/            # AlarmManager + BroadcastReceiver
├── ui/screen/               # Jetpack Compose screens
├── viewModel/               # PrayerViewModel
└── util/                    # Constants, UiState

data/
├── remote/                  # Retrofit API service + DTOs
├── local/                   # Room Database, DAO, Entity
├── mapper/                  # DTO ↔ Domain mappers
└── repository/              # PrayerTimeRepoImpl

domain/
├── model/                   # PrayerTimes, NextPrayer, Resource
├── repo/                    # PrayerTimeRepo interface
└── useCase/                 # GetPrayerTimesUseCase, GetNextPrayerUseCase
```

---

## 🌐 API

```
https://api.aladhan.com/v1/timingsByCity?city=Cairo&country=Egypt&method=5
```

---

## 🚀 How to Run

1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/prayer-times-app.git
```

2. Open the project in **Android Studio Hedgehog** or later

3. Let Gradle sync complete

4. Run on emulator or physical device (API 24+)

> ⚠️ Make sure the device/emulator has internet access on first launch to fetch and cache prayer times.

---

## 📋 Requirements

- Android Studio Hedgehog+
- Kotlin 2.1.20
- Min SDK: 24
- Target SDK: 36
- Java 17

---

## 📸 Screenshots
  <img src="https://github.com/user-attachments/assets/0f9c0f14-2000-419b-8168-c190c7748ad2" alt="Prayer Screen" width="300"/>
</p>

---

## 👩‍💻 Video


https://github.com/user-attachments/assets/6c642133-eed3-463c-b25d-d4afc308657b


**Doaa Mosalam**
