<script setup>
  import { ref } from "vue";

  const hintText = '';
  let userGuess = ref(hintText);

  const emit = defineEmits(['submit']);

  const handleSubmit = () => {
    emit('submit', userGuess.value);
  };

  defineExpose({
    userGuess
  });
</script>

<template>
  <div class="guess-bar">
    <input v-model="userGuess">
    <button type="submit" @click="handleSubmit">
      <img src="@/assets/icons/arrow-right.svg" alt="Search">
    </button>
  </div>
</template>

<style scoped>
  .guess-bar {
    display: flex;
    width: 100%;
    max-width: 500px;
    margin: 15px;
    flex-direction: row;
    justify-content: center;
    border: 1px solid #d3d3d3;
    border-radius: 12px;
    background: #181818;
    transition: 0.5s ease;
  }

  .guess-bar:hover {
    background: whitesmoke;
    border-color: #181818;
  }

  .guess-bar:hover button {
    visibility: visible;
  }

  input {
    flex-basis: 90%;
    height: 100%;
    background-color: transparent;
    color: whitesmoke;
    border: none;
    outline: none;
    padding-left: 20px;
  }

  .guess-bar:hover input {
    color: #181818;
  }

  button {
    flex: 1 1 10%;
    margin: 5px;
    padding: 5px;
    cursor: grab;
    visibility: hidden;
    background: transparent;
    border: none;
  }

  button img {
    width: 30px;
    height: 30px;
  }
</style>