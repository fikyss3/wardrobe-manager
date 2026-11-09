<script setup>
import { ref, onMounted } from 'vue';
import { getStats } from '../api';

const stats = ref({});
const type = ref('category');

const loadStats = async () => {
  try {
    const res = await getStats(type.value);
    stats.value = res.data;
  } catch (e) {
    console.error("Ошибка:", e);
  }
};

onMounted(loadStats);

const getTotal = () => {
  return Object.values(stats.value).reduce((a, b) => a + b, 0);
};
</script>

<template>
  <div class="stats-container">
    <h2 class="stats-title">Аналитика гардероба</h2>

    <div class="controls">
      <label>Группировать по:</label>
      <select v-model="type" @change="loadStats" class="select-input">
        <option value="category">Категории</option>
        <option value="dressCode">Дресс-коду</option>
      </select>
    </div>

    <div v-if="Object.keys(stats).length > 0" class="stats-list">
      <div v-for="(count, key) in stats" :key="key" class="stat-item">
        <div class="stat-label">
          <span class="stat-name">{{ key }}</span>
          <span class="stat-count">{{ count }} шт.</span>
        </div>
        <div class="progress-bar-bg">
          <div
            class="progress-bar-fill"
            :style="{ width: (count / getTotal() * 100) + '%' }"
          ></div>
        </div>
        <div class="stat-percent">{{ Math.round((count / getTotal() * 100)) }}%</div>
      </div>
    </div>

    <p v-else class="empty-state">Гардероб пуст. Добавьте вещи, чтобы увидеть статистику.</p>
  </div>
</template>

<style scoped>
.stats-container {
  max-width: 700px;
  margin: 0 auto;
  background: white;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.05);
}

.stats-title {
  margin-top: 0;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #111827;
}

.controls {
  margin-bottom: 2rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.select-input {
  padding: 0.5rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 0.95rem;
  background: white;
  cursor: pointer;
}

.stat-item {
  margin-bottom: 1.5rem;
}

.stat-label {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
  font-size: 0.95rem;
}

.stat-name {
  font-weight: 600;
  color: #374151;
}

.stat-count {
  color: #6b7280;
}

.progress-bar-bg {
  width: 100%;
  height: 12px;
  background-color: #f3f4f6;
  border-radius: 9999px;
  overflow: hidden;
}

.progress-bar-fill {
  height: 100%;
  background: linear-gradient(90deg, #10b981 0%, #34d399 100%);
  border-radius: 9999px;
  transition: width 0.5s ease-out;
}

.stat-percent {
  text-align: right;
  font-size: 0.8rem;
  color: #10b981;
  font-weight: 600;
  margin-top: 0.25rem;
}

.empty-state {
  text-align: center;
  color: #6b7280;
  padding: 2rem 0;
}
</style>