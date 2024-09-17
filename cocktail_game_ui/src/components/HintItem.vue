<script setup>
  const props = defineProps(
{
        hint: {
          type: String,
          default: '1. Hint text here'
        },
        hintNumber: {
          type: Number,
          default: 1
        }
    });

</script>

<template>
  <div class="hint-item">
    <h2>{{ hintNumber + 1 }}. {{ hint === "NULL" ? "<-- Image hint" : hint}}</h2>
  </div>
</template>

<style scoped>
  h2 {
    font-size: 14px;
    margin: 0 5px;
    text-align: justify;
  }
</style>