<script>
import request from "@/utils/request";

export default {
  name: "User",
  data() {
    return {
      fileList: [],
      tabledata: [],
      total: 0,
      pageNumber: 1,
      pageSize: 3,
      username: "",
      nickname: "",
      email: "",
      phone: "",
      address: "",
      multipleSelection: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
      dialogFormVisible2: false,
      form: {
        username: '',
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

    exportExecel() {
      window.open("http://localhost:9091/user/export")
    },

    importExcel() {
      this.dialogFormVisible2 = true
    },

    load() {
      request.get("/user/page", {
        params: {
          pageNum: this.pageNumber,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          email: this.email,
          phone: this.phone,
          address: this.address
        }
      }).then(res => {
        console.log(res)
        this.tabledata = res.records
        this.total = res.total
      })
    },
    reset() {
      this.username = ""
      this.nickname = ""
      this.email = ""
      this.phone = ""
      this.address = ""
      this.load()
    },
    addhandle() {
      this.dialogFormVisible = true
      this.form = {}
    },
    edithandle(row) {
      this.dialogFormVisible = true
      this.form = row
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    del(id) {
      request.delete("/user/delete/" + id).then(res => {
        if (res) {
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("删除错误")
        }
      })
    },
    delbatch() {
      let ids = this.multipleSelection.map(v => v.id)
      request.post("/user/delbatch", ids).then(res => {
        if (res) {
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },


    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNumber) {
      console.log(`当前页: ${pageNumber}`);
      this.pageNumber = pageNumber
      this.load()
    },

    save() {
      request.post("/user/save", this.form).then(res => {
        if (res) {
          this.$message.success("保持成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },

    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },

    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    handleExcel() {
      this.$message.success("导入成功")
      this.load()
    }

  },

}
</script>

<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4">
        <el-input suffix-icon="el-icon-search" placeholder="按账户搜索" v-model="username"></el-input>
      </el-col>
      <el-col :span="4">
        <el-input suffix-icon="el-icon-search" placeholder="按名字搜索" v-model="nickname"></el-input>
      </el-col>
      <el-col :span="4">
        <el-input suffix-icon="el-icon-search" placeholder="按邮箱搜索" v-model="email"></el-input>
      </el-col>
      <el-col :span="4">
        <el-input suffix-icon="el-icon-search" placeholder="按电话搜索" v-model="phone"></el-input>
      </el-col>
      <el-col :span="2">
        <el-button tpye="primary" @click="load">搜索</el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="warning" @click="reset">重置</el-button>
      </el-col>
    </el-row>
    <div>

      <el-button type="primary" @click="addhandle"> 新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="确定删除吗？"
          @confirm="delbatch">
        <el-button type="danger" slot="reference"> 批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-button type="primary" @click="importExcel"> 导入 <i class="el-icon-upload2"></i></el-button>
      <el-button type="primary" @click="exportExecel"> 导出 <i class="el-icon-download"></i></el-button>

    </div>

    <el-table :data="tabledata"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
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
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="waring" @click="edithandle(scope.row)">编辑</el-button>
          <el-popconfirm
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
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

    <el-dialog :visible.sync="dialogFormVisible">
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

    <el-dialog title="导入文件" :visible.sync="dialogFormVisible2">
      <el-upload
          class="upload-demo"
          action="http://localhost:9091/user/import"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="3"
          :file-list="fileList"
          :on-success="handleExcel">
        <el-button size="small" type="primary">点击上传</el-button>
        <div slot="tip" class="el-upload__tip">只能上传excel文件，且不超过500kb</div>
      </el-upload>
    </el-dialog>


  </div>
</template>

<style scoped>
.el-row {
  margin-bottom: 20px;

}

</style>