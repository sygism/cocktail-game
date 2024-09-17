<script setup>
const props = defineProps(
    {
      buttonText: {
        type: String,
        default: 'Text'
      }
    });
</script>

<template>
  <button class="activity-button">{{ buttonText }}</button>
</template>

<style scoped>
.activity-button {
  display: flex;
  width: 100%;
  max-width: 250px;
  margin: 15px;
  padding: 10px;
  flex-direction: row;
  justify-content: center;
  border: 1px solid #d3d3d3;
  border-radius: 20px;
  background: #181818;
  color: whitesmoke;
  font-size: 18px;
  font-weight: bold;
  transition: 0.5s ease;
}

.activity-button:hover {
  background: whitesmoke;
  border-color: #181818;
  color: #181818;
}
</style>