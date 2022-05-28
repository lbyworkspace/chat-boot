<template>
    <div class="wrap poac bgf">
        <h3 class="tc">网络在线聊天室</h3>
        <el-form ref="form" :model="formData" :rules="rules">
            <el-col :span="24">
                <el-form-item label="账号" prop="account">
                    <el-input v-model="formData.account" placeholder="请输入账号"/>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item label="密码" prop="password">
                    <el-input v-model="formData.password" type="password" placeholder="请输入密码" @keydown.enter.native="login "/>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item>
                    <el-button @click="$router.push('/register')">注册</el-button>
                    <el-button type="primary" @click="login">登录</el-button>
                </el-form-item>
            </el-col>
        </el-form>
    </div>
</template>

<script>
import {mapMutations,mapActions} from 'vuex'
export default {
    data(){
        return{
            formData:{
                account:'',
                password:''
            },
            rules:{
                account:[{required:true,message:'请输入账号',trigger: 'blur'}],
                password:[{required:true,message:'请输入密码',trigger: 'blur'}]
            }
        }
    },
    methods:{
        ...mapMutations(['setAccount']),
        ...mapActions(['setGlobalWs']),
        login(){
            this.$refs.form.validate(vali=>{
                if(vali){
                    this.$apis.login(this.formData).then(async ()=>{
                        this.$store.dispatch('login',this.formData).then(()=>{
                            this.$router.push('/main')
                        })
                    }).catch(err=>{
                        this.$message.error(err)
                    })
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.el-form{
    width: 300px;
    overflow: hidden;
    padding: 20px 20px 0px;
}
.el-col{
    // border: 1px solid red;
    text-align: center;
}
.wrap{
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 0 10px #E4E7ED;
}
</style>
