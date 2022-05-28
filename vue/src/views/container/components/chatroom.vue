<template>
    <div>
        <div class="df aic jcb header">
            <h4 class="title">{{currentChatObj.nickName}}</h4>
            <i class="el-icon-error cp" @click="setBoxType(null)"></i>
        </div>
        <div class="main" id="scrollview">
            <div 
                :class="['msg-item','df',item.uid==currentAccount.id?'reverse':'']" 
                v-for="item,index in chatroomInfo.data" 
                :key="index">
                <div class="image-box por">
                    <img class="avatar" :src="item.uid==currentAccount.id?currentAccount.avatarUrl:currentChatObj.avatarUrl">
                </div>
                <div class="content bgf">{{item.data}}</div>
            </div>
        </div>
        <div class="footer">
            <el-input 
            type="textarea" resize="none" 
            v-model="textareaInput" 
            :autosize="{ minRows: 6, maxRows: 6 }"
            @focus="scrollToBottom"
            @keyup.enter.native="test"></el-input>
        </div>
    </div>
</template>

<script>
import {mapGetters,mapMutations} from 'vuex'
import {set,get} from '@/utils/cookie.js'
export default {
    data(){
        return{
            list:[],
            chatroomInfo:null,
            textareaInput:'',
            SocketTask:null
        }
    },
    computed:{
        ...mapGetters(['currentChatObj','currentAccount'])
    },
    watch:{
        currentChatObj:{
            handler(val){
                console.log(val)
                this.initGroupId()
            }
        }
    },
    created(){
        this.initGroupId()
    },
    mounted(){
        this.$nextTick(()=>{
            this.scrollToBottom()
        })
    },
    destroyed(){
        console.log('destroyed')
        if(this.chatroomInfo.data.length) set(this.chatroomInfo.groupId,JSON.stringify(this.chatroomInfo.data))
        if(this.SocketTask) this.SocketTask.close()
    },
    methods:{
        ...mapMutations(['setBoxType']),
        initGroupId(){
            this.chatroomInfo = {groupId:'',data:[]}
            var arr = [this.currentChatObj.id,this.currentAccount.id]
            this.chatroomInfo.groupId = `|${arr.sort((a,b)=>{return a - b}).join('-')}|`
            let cookie = get(this.chatroomInfo.groupId)
            console.log(cookie)
            if(cookie) this.chatroomInfo.data = JSON.parse(cookie)
            this.SocketTask = new WebSocket(`${process.env.VUE_APP_BASEURL.replace('http','ws')}/chat/${this.chatroomInfo.groupId}/${this.currentAccount.id}`)
            this.SocketTask.onmessage = (res)=>{
                this.chatroomInfo.data.push(JSON.parse(res.data))
                this.$nextTick(()=>{
                    this.scrollToBottom()
                })
            }
        },
        scrollToBottom(){
            let el = document.getElementById('scrollview')
            el.scrollTo({top:el.scrollHeight,behavior:'smooth'})
        },
        test(){
            this.SocketTask.send(JSON.stringify({
                uid:this.currentAccount.id,
                data:this.textareaInput
            }))
            this.textareaInput = ''
        }
    }
}
</script>

<style lang="scss" scoped>
    .header{
        padding: 20px;
        border-bottom: 1px solid #E4E7ED;
        height: 60px;
    }
    .main{
        height: 490px;
        background-color: #f9f9f9;
        overflow-x: hidden;
        overflow-y: scroll;
        .msg-item{
            padding: 8px;
            .image-box{
                height: fit-content;
                .avatar{
                    border-radius: 4px;
                    width: 45px;
                    height: 45px;
                    margin-bottom: -5px;
                    // overflow: inherit;
                }
                &::before{
                    content: '';
                    display: block;
                    position: absolute;
                    top: 50%;
                    right: -50%;
                    background: #fff;
                    width: 12px;
                    height: 12px;
                    transform:translateY(-50%) rotate(45deg);
                }
                &::after{
                    content: '';
                    display: block;
                    position: absolute;
                    top: 50%;
                    left: -50%;
                    background: #fff;
                    width: 12px;
                    height: 12px;
                    transform:translateY(-50%) rotate(45deg);
                }
            }
            .content{
                text-align: justify;
                border-radius:4px;
                padding: 10px;
                margin: 0 15px;
                font-size: 15px;
                max-width: 60%;
                line-height: 25px;
                word-break: break-all;
            }
        }
        .reverse{
            flex-direction: row-reverse;
            color: #fff;
            &>.content{
                background: #5a9cf8;
            }
            &>.image-box::after,.image-box::before{
                background: #5a9cf8;
            }
        }
    }
    .footer{
        border-top: 1px solid #E4E7ED;
        height: 150px;
        overflow: hidden;
    }
</style>
