<template>
    <div class="por">
        <div class="bg"></div>
        <router-view />
    </div>
</template>

<style lang="scss" scoped>
.bg{
    background: url('../assets/bg.jpeg') center/cover no-repeat;
    filter: blur(3px);
    width: 100vw;
    height: 100vh;
}
</style>
