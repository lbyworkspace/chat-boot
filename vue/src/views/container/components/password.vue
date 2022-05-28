<template>
    <el-form ref="form" inline :model="defaultForm" :rules="rules">
        <el-col :span="24">
            <el-form-item label="请输入旧密码" label-width="120px" prop="oldpassword">
                <el-input v-model="defaultForm.oldpassword"></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="请输入新密码" label-width="120px" prop="password">
                <el-input v-model="defaultForm.password"></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="再次输入密码" label-width="120px" prop="repassword">
                <el-input v-model="defaultForm.repassword"></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="5" :offset="3">
            <el-form-item>
                <el-button type="primary" size="mini" @click="save">保存</el-button>
                <el-button size="mini" @click="setBoxType(null)">取消</el-button>
            </el-form-item>
        </el-col>
    </el-form>
</template>

<script>
import {mapGetters,mapMutations} from 'vuex'
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
            } else if (value != this.defaultForm.password) {
                callback(new Error('两次输入密码不一致'));
            } else {
                callback();
            }
        };
        return{
            defaultForm:{
                oldpassword:'',
                password:'',
                repassword:''
            },
            rules:{
                oldpassword:[{required:true,message:'请输入原密码',trigger: 'blur'}],
                password:[{required:true,validator: validatePass1,trigger: 'blur'}],
                repassword:[{required:true,validator: validatePass2,trigger: 'blur'}]
            }
        }
    },
    computed:{
        ...mapGetters(['currentAccount'])
    },
    created(){
        this.defaultForm = Object.assign({},this.currentAccount,this.defaultForm)
    },
    methods:{
        ...mapMutations(['setBoxType']),
        save(){
            this.$refs.form.validate(res=>{
                console.log(res)
                if(res){
                    this.$apis.update(this.defaultForm,'password').then(res=>{
                        console.log(res)
                        this.$message.success('修改成功,即将重新登录')
                        setTimeout(async () => {
                            await this.$store.dispatch('logout')
                            this.$router.push('/')
                        }, 1500);
                    }).catch(err=>{
                        console.log(err)
                        this.$message.error(err)
                    })
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
