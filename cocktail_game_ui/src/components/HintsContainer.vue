
<script setup>
import HintItem from '@/components/HintItem.vue';
import { computed } from 'vue';
const props = defineProps({
  hints: Array,
  preparingInstructions: String,
  hintImg: String
});

function isValidImageLink(link) {
  return link.match(/\.(jpeg|jpg|gif|png|bmp|webp)$/) != null;
}

let imageUrl = computed(() => {
  if (isValidImageLink(props.hintImg)) {
    return props.hintImg;
  } else if (props.hintImg === "UNDEFINED") {
    return "/src/assets/icons/question-mark.svg";
  } else {
    return "/src/assets/what-huh.gif";
  }
});
</script>

<template>
  <div class="hints-container">
    <div class="img-container">
      <img class="img" :src="imageUrl" alt="Cocktail image">
    </div>
    <div class="hints">
      <div class="hints-list">
        <h3>Preparing instructions</h3>
        <p>{{ preparingInstructions }}</p>
      </div>
      <div class="hints-list">
        <h3>Other hints</h3>
        <HintItem
            v-for="(hint, index) in hints"
            :key="index"
            :hint="hint"
            :hintNumber="index"
            class="hint-item"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
@keyframes slideIn {
  from {
    transform: translateX(-100%);
    opacity: 0;
  }
  to {
    transform: translateX(0);
    opacity: 1;
  }
}

.hints-container {
  display: flex;
  width: 75%;
  height: auto;
  margin: auto;
  justify-content: center;
}

.hints-container h3 {
  font-size: 20px;
  margin: 5px;
  font-family: "Lobster", sans-serif;
}

.hints-container .img-container {
  display: inline-block;
  width: 300px;
  height: 300px;
  margin: 5px;
}

.hints-container .img {
  border: 1px solid #282828;
  height: 300px;
  width: 300px;
}

.hints-container .hints {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  margin: 5px;
}

.hints-container .hints-list {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  max-height: 150px;
  overflow-y: auto;
}

.hints-container .hints-list p {
  font-size: 12px;
  text-align: justify;
  margin: 5px;
}

.hints-container .hints-list .hint-item {
  animation: slideIn 0.5s ease-out;
}
</style>