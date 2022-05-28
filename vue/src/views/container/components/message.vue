<template>
    <div class="wrap" v-if="currentAccount">
        <h5 style="margin-bottom:5px">账户信息</h5>
        <div class="list-item df aic cp" @click="setBoxType('edit')">
            <img :src="currentAccount.avatarUrl" />
            <div class="name ell">{{currentAccount.nickName}}</div>
            <el-button type="danger" size="mini" @click.stop="exit">退出</el-button>
        </div>
        <h5 style="margin-bottom:5px">在线列表</h5>
        <div v-for="v,i in total" :key="i" @click="$store.dispatch('toChatroom',{type:'chat',ob:v})">
            <div class="df aic list-item cp" v-if="v.account!=account">
                <img :src="v.avatarUrl" />
                <div class="name ell">{{v.nickName}}</div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters,mapMutations} from 'vuex'
export default {
    computed:{
        ...mapGetters(['total','account','currentAccount','GlobalWs','currentChatObj'])
    },
    watch:{
        currentChatObj:{
            handler(val){
                console.log(val)       
            }
        }
    },
    methods:{
        ...mapMutations(['setBoxType']),
        async exit(){
            await this.$store.dispatch('logout').then(()=>{
                this.$router.push('/login')
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.wrap{
    flex: 1;
    padding: 20px;
    .list-item{
        margin-bottom: 20px;
        img{
            border-radius: 50%;
            width: 40px;
            height: 40px;
            margin-right: 10px;
        }
        .name{
            width: 100px;
            font-size: 14px;
        }
        .msg{
            width: 110px;
            font-size: 12px;
            color: #C0C4CC;
        }
        .unreadNum{
            font-size: 12px;
            text-align: center;
            border-radius: 50%;
            background: red;
            font-weight: bold;
            width: 15px;
            height: 15px;
            line-height: 15px;
            color: white;
        }
        .time{
            font-size: 12px;
        }
    }
}
</style>