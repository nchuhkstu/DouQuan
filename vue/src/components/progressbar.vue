<template>
  <div class="progress-bar-container">
    <div class="progress-bar">
      <div class="progress" :style="{ width: progress + '%' }"></div>
    </div>
  </div>
    
</template>

<script>
export default {
  name: 'ProgressBar',
  props: {
    value: {
      type: Number,
      required: true,
      default: 0,
      validator: (value) => value >= 0 && value <= 102,
    },
  },
  computed: {
    progress() {
      return this.value;
    },
  },
};
</script>

<style>
.progress-bar-container{
  height: 13px;
  display: flex;
  flex-direction: column;
}
.progress-bar {
  width: 100%;
  height: 30%;
  background-color: rgb(120, 120, 120);
  border-radius: 4px;
  overflow: hidden;
  cursor: pointer;
  margin-top: auto;
}
.progress-bar:hover{
  height: 100%;
}

.progress {
  height: 100%;
  background-color: rgb(220, 220, 220);
  transition: width 0.3s linear;
}
</style>