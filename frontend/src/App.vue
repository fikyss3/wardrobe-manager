<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="app-container">
    <header class="main-header">
      <div class="header-inner">
        <div class="logo">Wardrobe Manager</div>
        <nav class="main-nav">
          <RouterLink to="/" class="nav-link">Гардероб</RouterLink>
          <RouterLink to="/add" class="nav-link">Добавить вещь</RouterLink>
          <RouterLink to="/stats" class="nav-link">Статистика</RouterLink>
        </nav>
      </div>
    </header>
    
    <main class="main-content">
      <RouterView />
    </main>
  </div>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

/* Жесткий сброс отступов для всей страницы */
*, *::before, *::after {
  box-sizing: border-box;
}

html, body, #app {
  margin: 0 !important;
  padding: 0 !important;
  width: 100% !important;
  min-width: 100% !important;
  overflow-x: hidden; /* Запрещаем горизонтальную прокрутку */
}

body {
  font-family: 'Inter', sans-serif;
  background-color: #f3f4f6;
  color: #1f2937;
  line-height: 1.5;
}

.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  width: 100%;
}

/* Шапка на всю ширину экрана */
.main-header {
  width: 100%;
  background: #ffffff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  position: sticky; /* Прилипает к верху при прокрутке */
  top: 0;
  z-index: 100;
}

/* Внутренний контейнер шапки теперь тоже на 100%, но с отступами по бокам */
.header-inner {
  width: 100%;
  padding: 1.2rem 3rem; /* Отступы слева и справа, чтобы текст не касался края экрана */
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 1.25rem;
  font-weight: 700;
  color: #111827;
  letter-spacing: 0.5px;
}

.main-nav {
  display: flex;
  gap: 1rem;
}

.nav-link {
  text-decoration: none;
  color: #6b7280;
  font-weight: 500;
  padding: 0.6rem 1.2rem;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.nav-link:hover {
  color: #10b981;
  background-color: #ecfdf5;
}

.nav-link.router-link-active {
  color: #059669;
  background-color: #d1fae5;
  font-weight: 600;
}

/* Основной контент */
.main-content {
  flex: 1;
  padding: 2rem 3rem;
  width: 100%;
}

/* Адаптивность для ноутбуков и планшетов */
@media (max-width: 1024px) {
  .header-inner {
    padding: 1rem 2rem;
  }
  .main-content {
    padding: 1.5rem 2rem;
  }
}

/* Адаптивность для телефонов */
@media (max-width: 768px) {
  .header-inner {
    flex-direction: column;
    gap: 1rem;
    padding: 1rem;
  }
  .main-content {
    padding: 1rem;
  }
}
</style>