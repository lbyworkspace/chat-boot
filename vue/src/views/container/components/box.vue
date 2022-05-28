<template>
    <div class="wrap por">
        <transition name="fade">
            <el-tabs v-if="type=='edit'" value="info">
                <el-tab-pane label="修改信息" name="info">
                    <info /> 
                </el-tab-pane>
                <el-tab-pane label="修改信息" name="password">
                    <password />
                </el-tab-pane>
            </el-tabs>
        </transition>
        <transition name="fade">
            <chatroom v-if="type=='chat'"></chatroom>
        </transition>
        <transition name="fade">
            <el-empty class="poac" v-if="!type" description="请选择在线用户或点击头像编辑信息"></el-empty>
        </transition>
    </div>
</template>

<script>
import info from './info.vue'
import password from './password.vue'
import Chatroom from './chatroom.vue'
export default {
    props:['type'],
    components:{
        info,password,
        Chatroom
    },
    data(){
        return{
            defaultForm:{
                nickName:'',
                password:''
            },
            textareaInput:'',
            rules:{
                nickName:[{required:true,message:'用户名不能为空',trigger: 'blur'}],
                password:[{required:true,message:'密码不能为空',trigger: 'blur'}]
            },
            list:[
                {
                    uid:1,
                    data:'你好'
                },{
                    uid:2,
                    data:'你好'
                },{
                    uid:1,
                    data:'你好'
                },{
                    uid:1,
                    data:'你好你好你好你好你好你好'
                },{
                    uid:2,
                    data:'你好'
                },{
                    uid:2,
                    data:'你好'
                } 
            ],
        }
    }
}
</script>

<style lang="scss" scoped>
.fade-enter-active, .fade-leave-active {
    transition: opacity .5s;
}
.fade-enter, .fade-leave-to {
    opacity: 0;
}
/deep/ .el-textarea__inner{
    border: none;
}
.wrap{
    flex: 4;
    border-left: 1px solid #E4E7ED; 
    .el-tabs{
        padding: 20px;
    }
    // .header{
    //     padding: 20px;
    //     border-bottom: 1px solid #E4E7ED;
    //     height: 60px;
    // }
    // .main{
    //     height: 490px;
    //     background-color: #f9f9f9;
    //     overflow-x: hidden;
    //     overflow-y: scroll;
    //     .msg-item{
    //         padding: 8px;
    //         .image-box{
    //             height: fit-content;
    //             .avatar{
    //                 border-radius: 4px;
    //                 width: 45px;
    //                 height: 45px;
    //                 margin-bottom: -5px;
    //                 // overflow: inherit;
    //             }
    //             &::before{
    //                 content: '';
    //                 display: block;
    //                 position: absolute;
    //                 top: 50%;
    //                 right: -50%;
    //                 background: #fff;
    //                 width: 12px;
    //                 height: 12px;
    //                 transform:translateY(-50%) rotate(45deg);
    //             }
    //             &::after{
    //                 content: '';
    //                 display: block;
    //                 position: absolute;
    //                 top: 50%;
    //                 left: -50%;
    //                 background: #fff;
    //                 width: 12px;
    //                 height: 12px;
    //                 transform:translateY(-50%) rotate(45deg);
    //             }
    //         }
    //         .content{
    //             text-align: justify;
    //             border-radius:4px;
    //             padding: 10px;
    //             margin: 0 15px;
    //             font-size: 15px;
    //             max-width: 60%;
    //             line-height: 25px;
    //             letter-spacing: 1px;
    //             word-break: break-all;
    //         }
    //     }
    //     .reverse{
    //         flex-direction: row-reverse;
    //         color: #fff;
    //         &>.content{
    //             background: #5a9cf8;
    //         }
    //         &>.image-box::after,.image-box::before{
    //             background: #5a9cf8;
    //         }
    //     }
    // }
    // .footer{
    //     border-top: 1px solid #E4E7ED;
    //     height: 150px;
    //     overflow: hidden;
    // }
}
</style>
