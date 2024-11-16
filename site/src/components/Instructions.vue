<script setup>
import { ref } from 'vue';

let showInfoBox = ref(false);

const showInstructions = () => { showInfoBox.value = true; }
const hideInstructions = () => { showInfoBox.value = false; }
const updateInstructionsLocation = (event) => {
  const infoBox = document.querySelector('.instructions');
  infoBox.style.top = `${event.clientY}px`;
  infoBox.style.left = `${event.clientX}px`;
}
</script>

<template>
  <div class="instructions-container">
    <h3>Try and guess the cocktail..</h3>
    <img
        src="@/assets/icons/info.svg"
        alt="Info Icon"
        @mouseover="showInstructions"
        @mousemove="updateInstructionsLocation"
        @mouseleave="hideInstructions"
    />
  </div>
  <div class="instructions" v-if="showInfoBox">
    <h3>How to play:</h3>
    <p>The goal of the game is to guess the name of the cocktail.<br>You have 5 guesses - with each incorrect guess
    a hint is provided.<br>At the start of the game, the name of the cocktail is partially revealed and it's preparing
      instructions are shown to you.<br>The game ends if you guess incorrectly 5 times.<br>Good luck!</p>
  </div>
</template>

<style scoped>
.instructions-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  width: auto;
  color: #181818;
  font-size: 18px;
}

.instructions-container h3 {
  font-size: 14px;
  margin: auto 5px;
}

.instructions-container img {
  width: 25px;
  height: 25px;
  margin: auto 5px;
}

.instructions {
  position: fixed;
  max-width: 250px;
  z-index: 1000;
  background-color: whitesmoke;
  border: 1px solid #181818;
  padding: 5px;
  font-size: 12px;
  text-align: center;
}

.instructions h3 {
  font-size: 14px;
  text-align: center;
  font-family: "Lobster", sans-serif;
}
</style>