<template>
  <div>
    <h1>用户{{ name }}注册成功！</h1>
    <p>恭喜您成功注册了账号。</p>
    <p>您可以继续登录或进行其他操作。</p>
    <label class="link" @click="handleLogin">前往登录</label>
  </div>
</template>

<script>
import router from "../modules/router";
export default {
  data() {
    return {
      name: this.$route.query.name,
      username: this.$route.query.username,
    };
  },
  methods: {
    handleLogin() {
      router.push('/');
    },
  },
};
</script>
<style src="../css/registAccess.css" scoped></style>