<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { createClothe } from '../api';

const router = useRouter();
const form = ref({
  name: '', description: '', category: 'TOP', dressCode: 'CASUAL', file: null
});

const submit = async () => {
  const formData = new FormData();
  formData.append('name', form.value.name);
  formData.append('description', form.value.description);
  formData.append('category', form.value.category);
  formData.append('dressCode', form.value.dressCode);
  formData.append('file', form.value.file);

  try {
    await createClothe(formData);
    alert('Вещь успешно добавлена!');
    router.push('/');
  } catch (e) {
    alert('Ошибка: ' + (e.response?.data?.error || e.message));
  }
};
</script>

<template>
  <div class="form-container">
    <h2 class="form-title">Добавить новую вещь</h2>
    <form @submit.prevent="submit" class="add-form">
      <div class="form-group">
        <label>Название</label>
        <input v-model="form.name" placeholder="Например: Белая классическая рубашка" required class="input-field" />
      </div>

      <div class="form-group">
        <label>Описание</label>
        <textarea v-model="form.description" placeholder="Материал, бренд или заметки" rows="3" class="input-field textarea"></textarea>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label>Категория</label>
          <select v-model="form.category" class="input-field">
            <option value="TOP">Верх</option>
            <option value="BOTTOM">Низ</option>
            <option value="SHOES">Обувь</option>
            <option value="ACCESSORIES">Аксессуары</option>
            <option value="OUTERWEAR">Верхняя одежда</option>
          </select>
        </div>

        <div class="form-group">
          <label>Дресс-код</label>
          <select v-model="form.dressCode" class="input-field">
            <option value="CASUAL">Повседневная</option>
            <option value="FORMAL">Официально-деловая</option>
            <option value="HOME">Домашняя</option>
            <option value="SPORT">Спортивная</option>
          </select>
        </div>
      </div>

      <div class="form-group">
        <label>Фотография</label>
        <input type="file" @change="e => form.file = e.target.files[0]" accept="image/*" required class="file-input" />
      </div>

      <button type="submit" class="submit-btn">Сохранить в гардероб</button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  max-width: 500px;
  margin: 0 auto;
  background: white;
  padding: 2rem;
  border-radius: 16px;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.05);
}

.form-title {
  margin-top: 0;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #111827;
  text-align: center;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
}

.input-field {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 0.95rem;
  font-family: inherit;
  box-sizing: border-box;
  transition: border-color 0.2s, box-shadow 0.2s;
}

.input-field:focus {
  outline: none;
  border-color: #10b981;
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
}

.textarea {
  resize: vertical;
  min-height: 80px;
}

.file-input {
  width: 100%;
  padding: 0.5rem;
  border: 1px dashed #d1d5db;
  border-radius: 8px;
  background: #f9fafb;
  cursor: pointer;
}

.submit-btn {
  width: 100%;
  padding: 0.875rem;
  background-color: #10b981;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.1s;
  margin-top: 0.5rem;
}

.submit-btn:hover {
  background-color: #059669;
}

.submit-btn:active {
  transform: scale(0.98);
}
</style>