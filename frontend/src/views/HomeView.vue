<script setup>
import { ref, onMounted } from 'vue';
import { getClothes, deleteClothe, updateClothe } from '../api';

const clothes = ref([]);
const filter = ref('ALL');
const editingItem = ref(null);
const editForm = ref({
  name: '',
  description: '',
  category: 'TOP',
  dressCode: 'CASUAL'
});

const loadClothes = async () => {
  try {
    const res = await getClothes();
    clothes.value = res.data;
  } catch (e) {
    console.error("Ошибка загрузки:", e);
  }
};

onMounted(loadClothes);

const filteredClothes = () => {
  if (filter.value === 'ALL') return clothes.value;
  return clothes.value.filter(c => c.dressCode === filter.value);
};

const getBadgeColor = (type, value) => {
  if (type === 'dressCode') {
    const colors = { CASUAL: '#e0533a', FORMAL: '#6366f1', HOME: '#f59e0b', SPORT: '#10b981' };
    return colors[value] || '#6b7280';
  }
  return '#8b5cf6';
};

const handleDelete = async (id) => {
  if (confirm('Вы уверены, что хотите удалить эту вещь?')) {
    try {
      await deleteClothe(id);
      clothes.value = clothes.value.filter(item => item.id !== id);
    } catch (e) {
      alert('Ошибка при удалении: ' + (e.response?.data?.error || e.message));
    }
  }
};

const openEditModal = (item) => {
  editingItem.value = item;
  editForm.value = {
    name: item.name,
    description: item.description || '',
    category: item.category,
    dressCode: item.dressCode
  };
};

const closeEditModal = () => {
  editingItem.value = null;
};

const handleUpdate = async () => {
  try {
    const updated = await updateClothe(editingItem.value.id, editForm.value);
    const index = clothes.value.findIndex(item => item.id === updated.data.id);
    if (index !== -1) {
      clothes.value[index] = updated.data;
    }
    closeEditModal();
    alert('Вещь обновлена!');
  } catch (e) {
    alert('Ошибка при обновлении: ' + (e.response?.data?.error || e.message));
  }
};
</script>

<template>
  <div>
    <div class="filter-bar">
      <label>Фильтр по дресс-коду:</label>
      <select v-model="filter" class="select-input">
        <option value="ALL">Все вещи</option>
        <option value="CASUAL">Повседневная</option>
        <option value="FORMAL">Деловая</option>
        <option value="HOME">Домашняя</option>
        <option value="SPORT">Спортивная</option>
      </select>
    </div>

    <div class="clothes-grid">
      <div v-for="item in filteredClothes()" :key="item.id" class="clothing-card">
        <div class="image-wrapper">
          <img :src="'http://localhost:8080' + item.imageUrl" alt="Вещь" />
        </div>
        <div class="card-body">
          <h3 class="item-name">{{ item.name }}</h3>
          <div class="badges">
            <span class="badge" :style="{ backgroundColor: getBadgeColor('category', item.category) + '20', color: getBadgeColor('category', item.category) }">
              {{ item.category }}
            </span>
            <span class="badge" :style="{ backgroundColor: getBadgeColor('dressCode', item.dressCode) + '20', color: getBadgeColor('dressCode', item.dressCode) }">
              {{ item.dressCode }}
            </span>
          </div>
          <p class="item-desc">{{ item.description || 'Нет описания' }}</p>

          <div class="card-actions">
            <button @click="openEditModal(item)" class="btn-edit">Редактировать</button>
            <button @click="handleDelete(item.id)" class="btn-delete">Удалить</button>
          </div>
        </div>
      </div>
    </div>

    <p v-if="filteredClothes().length === 0" class="empty-state">
      Вещи не найдены. <RouterLink to="/add">Добавьте первую!</RouterLink>
    </p>

    <!-- Модальное окно редактирования -->
    <div v-if="editingItem" class="modal-overlay" @click="closeEditModal">
      <div class="modal-content" @click.stop>
        <h2>Редактировать вещь</h2>
        <form @submit.prevent="handleUpdate" class="edit-form">
          <div class="form-group">
            <label>Название</label>
            <input v-model="editForm.name" required class="input-field" />
          </div>

          <div class="form-group">
            <label>Описание</label>
            <textarea v-model="editForm.description" rows="3" class="input-field textarea"></textarea>
          </div>

          <div class="form-row">
            <div class="form-group">
              <label>Категория</label>
              <select v-model="editForm.category" class="input-field">
                <option value="TOP">Верх</option>
                <option value="BOTTOM">Низ</option>
                <option value="SHOES">Обувь</option>
                <option value="ACCESSORIES">Аксессуары</option>
                <option value="OUTERWEAR">Верхняя одежда</option>
              </select>
            </div>

            <div class="form-group">
              <label>Дресс-код</label>
              <select v-model="editForm.dressCode" class="input-field">
                <option value="CASUAL">Повседневная</option>
                <option value="FORMAL">Официально-деловая</option>
                <option value="HOME">Домашняя</option>
                <option value="SPORT">Спортивная</option>
              </select>
            </div>
          </div>

          <div class="modal-actions">
            <button type="button" @click="closeEditModal" class="btn-cancel">Отмена</button>
            <button type="submit" class="btn-save">Сохранить</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.filter-bar {
  margin-bottom: 1.5rem;
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
  outline: none;
}

.select-input:focus {
  border-color: #10b981;
  box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
}

.clothes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 1.5rem;
}

.clothing-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05), 0 2px 4px -1px rgba(0, 0, 0, 0.03);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.clothing-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
}

.image-wrapper {
  height: 200px;
  overflow: hidden;
  background: #f9fafb;
  display: flex;
  align-items: center;
  justify-content: center;
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  padding: 10px;
  box-sizing: border-box;
}

.card-body {
  padding: 1rem;
}

.item-name {
  margin: 0 0 0.75rem 0;
  font-size: 1.1rem;
  font-weight: 600;
  color: #111827;
}

.badges {
  display: flex;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
  flex-wrap: wrap;
}

.badge {
  font-size: 0.75rem;
  font-weight: 600;
  padding: 0.25rem 0.6rem;
  border-radius: 9999px;
  text-transform: uppercase;
  letter-spacing: 0.025em;
}

.item-desc {
  margin: 0 0 1rem 0;
  color: #6b7280;
  font-size: 0.875rem;
  line-height: 1.4;
}

.card-actions {
  display: flex;
  gap: 0.5rem;
}

.btn-edit, .btn-delete {
  flex: 1;
  padding: 0.5rem;
  border: none;
  border-radius: 6px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-edit {
  background: #e0e7ff;
  color: #4338ca;
}

.btn-edit:hover {
  background: #c7d2fe;
}

.btn-delete {
  background: #fee2e2;
  color: #dc2626;
}

.btn-delete:hover {
  background: #fecaca;
}

.empty-state {
  text-align: center;
  color: #6b7280;
  margin-top: 3rem;
  font-size: 1.1rem;
}

.empty-state a {
  color: #10b981;
  text-decoration: none;
  font-weight: 600;
}

/* Стили модального окна */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 16px;
  max-width: 500px;
  width: 90%;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-content h2 {
  margin-top: 0;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #111827;
}

.edit-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  margin-bottom: 0;
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

.modal-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.btn-cancel, .btn-save {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.btn-cancel {
  background: #f3f4f6;
  color: #4b5563;
}

.btn-cancel:hover {
  background: #e5e7eb;
}

.btn-save {
  background: #10b981;
  color: white;
}

.btn-save:hover {
  background: #059669;
}
</style>