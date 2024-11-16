<script setup>
import {computed, onMounted, ref} from 'vue';
import SearchBar from '@/components/GuessBar.vue';
import Header from '@/components/Header.vue';
import Instructions from '@/components/Instructions.vue';
import HintsContainer from '@/components/HintsContainer.vue';
import ActivityButton from '@/components/ActivityButton.vue';
import Stats from '@/components/Stats.vue';
import {handleError} from '@/exceptions/errorHandler.js';
import {startGame, getGameState, submitGuess, getHighScore} from '@/services/gameService.js';

const hints = ref([]);
const hintedWord = ref('');
const guessesLeft = ref(5);
const currentScore = ref(0);
const gameStatus = ref("GAME_NOT_STARTED");
const currentGameState = ref(null);
const imgHint = ref("UNDEFINED");
const highScore = ref(0);
const instructionsHint = ref('');

const loadGameState = async () => {
  const {
    hints: newHints,
    hintedWord: newHintedWord,
    hintsLeft,
    hintInstructions,
    currentScore: newCurrentScore
  } = currentGameState.value;
  const filteredHints = newHints.filter(hint => !hints.value.includes(hint));
  if (filteredHints.length > 0) {
    const newHint = filteredHints[0];
    imgHint.value = newHint === "NULL" || newHint.includes('http') ? newHint : imgHint.value;
    hints.value.push(newHint);
  }
  hintedWord.value = newHintedWord;
  guessesLeft.value = hintsLeft;
  instructionsHint.value = hintInstructions;
  currentScore.value = newCurrentScore;
};

const resetBoard = () => {
  updateHighScore();
  hints.value = [];
  hintedWord.value = '';
  imgHint.value = 'UNDEFINED';
  guessesLeft.value = 5;
  currentScore.value = 0;
};

const handleStartGame = async () => {
  try {
    resetBoard();
    const {data} = await startGame();
    gameStatus.value = data.gameStatus;
    if (gameStatus.value === "GAME_RUNNING") {
      currentGameState.value = data.gameState;
      await loadGameState();
    }
  } catch (error) {
    handleError(error);
  }
};

const handleGetGameState = async () => {
  try {
    resetBoard();
    const {data} = await getGameState();
    gameStatus.value = data.gameStatus;
    currentGameState.value = data.gameState;
    if (gameStatus.value === "GAME_RUNNING") {
      await loadGameState();
    }
  } catch (error) {
    handleError(error);
  }
};

const handleSubmitGuess = async (guess) => {
  try {
    const {data} = await submitGuess(guess);
    currentGameState.value = data.gameState;
    gameStatus.value = data.gameStatus;
    await loadGameState();
  } catch (error) {
    handleError(error);
  }
};

const updateHighScore = async () => {
  try {
    const {data} = await getHighScore();
    highScore.value = data;
  } catch (error) {
    handleError(error);
  }
};

const formattedHintedWord = computed(() => hintedWord.value.replace(/ /g, '   ').replace(/_/g, '_ '));

onMounted(async () => {
  await handleGetGameState();
});
</script>

<template>
  <Header/>
  <Instructions v-if="gameStatus !== 'GAME_WON' && gameStatus !== 'GAME_LOST'"/>
  <div v-if="gameStatus === 'GAME_RUNNING'">
    <Stats :guessesLeft="guessesLeft" :currentScore="currentScore" :highScore="highScore"/>
    <HintsContainer
        :hints="hints"
        :preparingInstructions="instructionsHint"
        :hintImg="imgHint"
    />
    <div class="word-container">
      <h3>{{ formattedHintedWord }}</h3>
    </div>
  </div>
  <div v-if="gameStatus === 'GAME_RUNNING'" class="container-fluid">
    <SearchBar @submit="handleSubmitGuess"/>
  </div>
  <div v-if="gameStatus === 'GAME_NOT_STARTED'" class="container-fluid">
    <ActivityButton
        @click="handleStartGame"
        buttonText="Start Game"
    />
  </div>
  <div class="round-over" v-if="gameStatus === 'GAME_WON'">
    <h3>Round won! 🎉</h3>
    <div class="container-fluid">
      <ActivityButton
          @click="handleGetGameState"
          buttonText="Next round"
      />
    </div>
  </div>
  <div class="round-over" v-if="gameStatus === 'GAME_LOST'">
    <h3>Game over! 😭</h3>
    <div class="container-fluid">
      <ActivityButton
          @click="handleStartGame"
          buttonText="New game"
      />
    </div>
  </div>
</template>

<style scoped>
.container-fluid {
  display: flex;
  height: auto;
  width: 75%;
  margin: auto;
  justify-content: center;
  background-color: transparent;
}

.word-container {
  display: block;
  flex-basis: 100%;
  justify-content: center;
  align-items: center;
  margin: 5px;
  transition: ease 0.3s;
}

.word-container h3 {
  font-size: 24px;
  text-align: center;
}

.word-container h4 {
  font-size: 14px;
  text-align: center;
}

.round-over {
  text-align: center;
}

.round-over h3 {
  font-size: 20px;
  text-align: center;
  margin: 10px;
}
</style>