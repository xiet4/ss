<template>
  <div>
    <el-container style="height: 100% ; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246);height: 100%">
        <el-menu :default-openeds="['1', '3']">
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>导航一</template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>导航二</template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i>导航三</template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <el-dropdown>
            <i class="el-icon-setting" style="margin-right: 15px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>查看</el-dropdown-item>
              <el-dropdown-item>新增</el-dropdown-item>
              <el-dropdown-item>删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <span>王小虎</span>
        </el-header>

        <el-main>
          <div>
            <el-input style="width: 300px" suffix-icon="el-icon-search" placeholder="按账户搜索" v-model="username"></el-input>
            <el-input style="width: 300px" suffix-icon="el-icon-search" placeholder="按名字搜索" v-model="nickname"></el-input>
            <el-input style="width: 300px" suffix-icon="el-icon-search" placeholder="按邮箱搜索" v-model="email"></el-input>
            <el-input style="width: 300px" suffix-icon="el-icon-search" placeholder="按电话搜索" v-model="phone"></el-input>

            <el-button tpye="primary" @click="load">搜索 </el-button>
            <el-button type="warning" @click="reset">重置 </el-button>
          </div>
          <div>
            <el-button type="primary" @click="addreset"> 新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button  type="danger" > 删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button  type="primary" > 导入 <i class="el-icon-upload2"></i></el-button>
            <el-button  type="primary" > 导出 <i class="el-icon-download"></i></el-button>
          </div>

          <el-table :data="tabledata">
            <el-table-column prop="username" label="账号" width="140">
            </el-table-column>
            <el-table-column prop="nickname" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column  label="操作">
                <el-button type="waring">编辑</el-button>
                <el-button type="danger">删除</el-button>
            </el-table-column>

          </el-table>
          <div>
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page=pageNumber
                :page-sizes="[3, 5, 10, 20]"
                :page-size=pageSize
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
          </div>

          <el-dialog title="新增" :visible.sync="dialogFormVisible">
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
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>


        </el-main>

      </el-container>

    </el-container>
  </div>

</template>

<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>

import request from "@/utils/request";

export default {
  name: 'HomeView',
  data() {
    return {
      tabledata:[],
      total:0,
      pageNumber:1,
      pageSize:3,
      username:"",
      nickname:"",
      email:"",
      phone:"",
      address:"",

      dialogTableVisible: false,
      dialogFormVisible: false,
      form: {
        username:'',
        nickname: '',
        email: '',
        phone: '',
        address: '',
      },
      formLabelWidth: '120px'
    }
  },


  created() {
    this.load()
  },

  methods: {
    load(){
      request.get("/user/page",{
        params: {
          pageNum: this.pageNumber,
          pageSize: this.pageSize,
          username:this.username,
          nickname:this.nickname,
          email:this.email,
          phone:this.phone,
          address:this.address
        }
      }).then(res=>{
        console.log(res)
        this.tabledata=res.records
        this.total=res.total
      })
    },
    reset(){
      this.username=""
      this.nickname=""
      this.email=""
      this.phone=""
      this.address=""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNumber) {
      console.log(`当前页: ${pageNumber}`);
      this.pageNumber=pageNumber
      this.load()
    },

    addreset(){
      this.dialogFormVisible = true
      this.form={}
    },
    save(){
      request.post("/user/save",this.form).then(res=>{
        if(res){
          this.$message.success("保持成功")
          this.dialogFormVisible=false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    }

  },
}

</script>
