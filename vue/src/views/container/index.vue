<template>
    <div class="wrapper poac bgf df">
        <message /> 
        <box :type="boxType"/>
    </div>
</template>

<script>
import box from './components/box.vue'
import message from './components/message.vue'
import {mapGetters} from 'vuex'
export default {
    components:{
        box,message
    },
    computed:{
        ...mapGetters(['boxType'])
    }
}
</script>

<style lang="scss" scoped>
.wrapper{
    width: 1000px;
    height: 700px;
    border-radius: 10px;
    // padding: 20px;
    box-shadow: 0 0 10px #E4E7ED;
}
</style>
