<template>
  <div class="login-container">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px" class="login-form" >
      <h2 class="login-title">管理系统</h2>
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="form.password"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import request from "@/utils/request";

export default {
  name:"Login",
  data() {
    return {
      form: {
        username: "",
        password: ""
      },
      formLabelWidth: '120px',

      rules: {
        username: [
          { required: true, message: '请输用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输密码', trigger: 'blur' },
        ],
      }
    };

  },
  methods: {
    onSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res=>{
            if(res.code==="200"){
              console.log(res)
              localStorage.setItem("user",JSON.stringify(res.data))
              this.$message.success("登录成功")
              this.$router.push("/")
            }else {
              this.$message.error("登录失败")
            }
          })
        } else {
          return false;
        }
      });
    },

  }
};
</script>

<style acoped>
.login-form {
  width: 350px;
  margin: 160px auto; /* 上下间距160px，左右自动居中*/
  background-color: rgb(255, 255, 255, 0.8); /* 透明背景色 */
  padding: 30px;
  border-radius: 20px; /* 圆角 */
}

/* 背景 */
.login-container {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #B3C0D1;
}

/* 标题 */
.login-title {
  color: #303133;
  text-align: center;
}
</style>