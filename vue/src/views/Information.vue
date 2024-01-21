<script>
import request from "@/utils/request";

export default {
  name: "Information",
  data() {
    return {
      form:{},
      user: JSON.parse(localStorage.getItem("user")),
      formLabelWidth: '120px'
    }

  },

  created() {
    request.get("/user/find/"+this.user.username).then(res => {
      console.log(res)
      this.form=res

    })
  },
}
</script>

<template>
<div>
  <el-form :model="form">
    <el-form-item label="账户" :label-width="formLabelWidth">
      <el-input v-model="form.username" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="昵称" :label-width="formLabelWidth">
      <el-input v-model="form.nickname" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" :label-width="formLabelWidth">
      <el-input v-model="form.email" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="手机号" :label-width="formLabelWidth">
      <el-input v-model="form.phone" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="地址" :label-width="formLabelWidth">
      <el-input v-model="form.address" autocomplete="off"></el-input>
    </el-form-item>

  </el-form>
</div>
</template>

<style scoped>

</style>