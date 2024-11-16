<script setup>
  defineProps({
    guessesLeft: Number,
    currentScore: Number,
    highScore: Number
  });
</script>

<template>
  <div class="stats-container">
    <h3>Guesses left: {{ guessesLeft === null ? 0 : guessesLeft }}</h3>
    <h3>Current score: {{ currentScore === null ? 0 : currentScore }}</h3>
    <h3>High-score: {{ highScore === null ? 0 : highScore }}</h3>
  </div>
</template>

<style scoped>
  .stats-container {
    display: flex;
    width: 75%;
    justify-content: center;
    margin: auto;
  }

  .stats-container h3 {
    font-size: 20px;
    margin: 5px;
    font-family: "Lobster", sans-serif;
  }
</style>