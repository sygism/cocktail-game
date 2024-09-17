<script setup>
  defineProps({
    error: String,
    id: Number,
    callbackCloseSelf: Function,
    callbackCloseAll: Function
  });
</script>

<template>
  <div class="error-popup">
    <div class="error-popup-content">
      <img src="@/assets/icons/alert.svg" alt="alert" />
      <p>An error has occured! {{ error }}</p>
      <a @click="callbackCloseAll">Close all</a>
      <button @click="callbackCloseSelf(id)">
        <img src="@/assets/icons/close.svg" alt="close" />
      </button>
    </div>
  </div>
</template>

<style scoped>
  @keyframes fadeInDown {
    from {
      opacity: 0;
      transform: translateY(-20px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
  .error-popup {
    width: 100%;
    height: auto;
    justify-content: center;
    align-items: center;
    border: 1px solid darkred;
    border-radius: 16px;
    background-color: #FFCCCB;
    z-index: 1000;
    font-size: 12px;
    color: red;
    margin-top: 10px;
    animation: fadeInDown 0.5s ease-in-out;
  }

  .error-popup .error-popup-content {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .error-popup-content button {
    background: transparent;
    border: none;
    width: 10%;
  }

  .error-popup-content img {
    width: 25px;
    height: 25px;
    margin: 5px;
  }

  .error-popup-content a {
    color: darkgray;
    margin: auto;
    flex-basis: 10%;
  }

  .error-popup-content a:hover {
    color: black;
    cursor: grab;
  }

  .error-popup-content p {
    margin: auto;
    flex-basis: 80%;
    text-align: start;
  }
</style>