<template>
    <el-form ref="form" inline :model="defaultForm" :rules="rules">
        <el-col :span="24">
            <el-form-item label="用户名" label-width="100px" prop="nickName">
                <el-input v-model="defaultForm.nickName"></el-input>
            </el-form-item>
        </el-col>
        <el-col :span="24">
            <el-form-item label="头像" label-width="100px" prop="avatarUrl">
                <el-upload
                    class="avatar-uploader"
                    :show-file-list="false"
                    action="http://localhost:9000/user/upload"
                    :on-success="handleSuccess">
                    <img v-if="defaultForm.avatarUrl" :src="defaultForm.avatarUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
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
import {mapMutations,mapGetters} from 'vuex'
export default {
    data(){
        return{
            defaultForm:{
                nickName:'',
                avatarUrl:''
            },
            templateFile:null,
            imageUrl:'',
            rules:{
                nickName:[{required:true,message:'用户名不能为空',trigger: 'blur'}],
                avatarUrl:[{required:true,message:'图片不能为空'}]
            }
        }
    },
    computed:{
        ...mapGetters(['currentAccount','total'])
    },
    created(){
        this.defaultForm = Object.assign({},this.currentAccount,this.defaultForm)
    },
    methods:{
        ...mapMutations(['setBoxType']),
        handleSuccess(res){
            this.defaultForm.avatarUrl = res
        },
        save(){
            this.$refs.form.validate(res=>{
                console.log(res)
                if(res){
                    this.$apis.update(this.defaultForm,'info').then(()=>{
                        let currentAccount = this.total.find(v=>v.id==this.defaultForm.id)
                        currentAccount.nickName = this.defaultForm.nickName
                        currentAccount.avatarUrl = this.defaultForm.avatarUrl
                        this.$store.commit('setBoxType',null)
                        this.$message.success('修改成功')
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
.avatar-uploader /deep/ .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader /deep/ .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
