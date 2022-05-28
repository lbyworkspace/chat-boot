<template>
    <div class="wrap poac bgf">
        <h3 class="tc">注册用户</h3>
        <el-form ref="form" :model="formData" :rules="rules">
            <el-col :span="24">
                <el-form-item label="请输入账号" label-width="110px" prop="account">
                    <el-input v-model="formData.account"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item label="请输入用户名" label-width="110px" prop="nickName">
                    <el-input v-model="formData.nickName"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item label="请输入密码" label-width="110px" prop="password">
                    <el-input v-model="formData.password"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item label="再次输入密码" label-width="110px" prop="repassword">
                    <el-input v-model="formData.repassword"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="24">
                <el-form-item>
                    <el-button @click="$router.back()">返回</el-button>
                    <el-button type="primary" @click="register">注册</el-button>
                </el-form-item>
            </el-col>
        </el-form>
    </div>
</template>

<script>
export default {
    data(){
        var validatePass1 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value != this.formData.password) {
                callback(new Error('两次输入密码不一致'));
            } else {
                callback();
            }
        };
        return{
            formData:{
                account:'',
                password:'',
                repassword:'',
                nickName:'',
                id:null,
                avatarUrl:'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
            },
            rules:{
                account:[{required:true,message:'请输入账号',trigger: 'blur'}],
                nickName:[{required:true,message:'请输入用户名',trigger: 'blur'}],
                password:[{required:true,validator: validatePass1,trigger: 'blur'}],
                repassword:[{required:true,validator: validatePass2,trigger: 'blur'}]
            }
        }
    },
    methods:{
        register(){
            this.$refs.form.validate(vali=>{
                if(vali){
                    this.$apis.register(this.formData).then(()=>{
                        this.$message.success('注册成功，即将跳转登录页面')
                        setTimeout(() => {
                            this.$router.push('/login')
                        }, 1500);
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
