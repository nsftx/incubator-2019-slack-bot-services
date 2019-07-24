<template>
  <div id="app">
    
    <!-- 
      <img alt="Vue logo" src="./assets/logo.png">
      <HelloWorld msg="Welcome to Your Vue.js App"/>
    -->
    <login />
  </div>
</template>

<script>
import login from "./components/login.vue";

export default {
  name: 'app',
  components: {
    login
  }
}
</script>

<style>
*{
  box-sizing: border-box;
  margin: 0px;
}

body, html{
  height: 100%;
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
}

</style>
