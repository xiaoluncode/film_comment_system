<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入电影名称查询" style="width: 300px"/>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="primary" @click="reset">重置</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-button type="primary" style="margin-bottom: 10px" @click="handleAdd">新增</el-button>
    </div>
    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" stripe style="width: 100%">
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="cover" label="封面" >
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 40px; height: 40px; border-radius: 5px" :preview-src-list="[scope.row.cover]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="descr" label="描述" show-overflow-tooltip />
        <el-table-column prop="year" label="年份" />
        <el-table-column prop="director" label="导演" />
        <el-table-column prop="actors" label="演员" show-overflow-tooltip/>
        <el-table-column prop="categoryName" label="类型分类" />
        <el-table-column prop="country" label="国家" />
        <el-table-column prop="language" label="语言" />
        <el-table-column prop="date" label="上映日期" />
        <el-table-column prop="duration" label="时长" />
        <el-table-column prop="score" label="综合评分" />

        <el-table-column  label="操作" width="160">
          <template #default="scope">
            <el-button  type="primary"  @click="handleEdit(scope.row)">编辑</el-button>
            <el-button  type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <div class="card">
      <el-pagination background layout="total,prev, pager, next"
                     v-model:page-size="data.pageSize" v-model:current-page="data.pageNum"
                     :total="data.total" @current-change="load" />
    </div>

    <el-dialog v-model="data.formVisible" title="电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 20px">
        <el-form-item label="名称" >
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入电影名称" />
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述" >
          <el-input :rows="4" type="textarea" v-model="data.form.descr" autocomplete="off" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="年份" >
          <el-input
              v-model.number="data.form.year"
              autocomplete="off"
              placeholder="请输入4位年份"
              maxlength="4"
              oninput="value=value.replace(/[^0-9]/g,'').slice(0,4)"/>
        </el-form-item>
        <el-form-item label="导演" >
          <el-input v-model="data.form.director" autocomplete="off" placeholder="请输入导演名称" />
        </el-form-item>
        <el-form-item label="演员" >
          <el-input :rows="3"  type="textarea" v-model="data.form.actors" autocomplete="off" placeholder="请输入演员名称" />
        </el-form-item>
        <el-form-item label="分类名称">
          <el-select v-model="data.form.categoryName" placeholder="请选择分类名称">
            <el-option
                v-for="category in categories"
                :key="category.id"
                :label="category.name"
                :value="category.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="国家" >
          <el-input v-model="data.form.country" autocomplete="off" placeholder="请输入国家名称" />
        </el-form-item>
        <el-form-item label="语言" >
          <el-input v-model="data.form.language" autocomplete="off" placeholder="请输入语言" />
        </el-form-item>
        <el-form-item label="上映日期" >
          <el-date-picker style="width: 100%" type="date" v-model="data.form.date" placeholder="请选择上映日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD"></el-date-picker>
        </el-form-item>
        <el-form-item label="片长" >
          <el-input v-model="data.form.duration" autocomplete="off" placeholder="请输入片长" />
        </el-form-item>
        <el-form-item label="综合评分" >
          <el-input v-model="data.form.score" autocomplete="off" placeholder="请输入电影综合评分" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request";
import { ElMessage, ElMessageBox } from "element-plus";

// 文件上传的接口地址
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

const categories = ref([])
const data = reactive({
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  name: null,
  formVisible: false,
  form: {}
})
const loadCategories = () => {
  request.get('/category/selectAll').then(res => {
    categories.value = res.data
  })
}

const load = () => {
  request.get('/film/selectPage',{
    params:{
      pageNum:data.pageNum,
      pageSize:data.pageSize,
      name:data.name
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()
loadCategories()

const reset = () => {
  data.name = null
  load()
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true // 修改: data.fromVisible = false -> data.formVisible = true
}

const add = () => {
  request.post('/film/add', data.form).then(res => {
    console.log(res)
    if (res.code === '200') {
      load()
      data.formVisible = false
      ElMessage.success('添加成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true // 修改: 确保逻辑一致
}
const del = (id) => {
  ElMessageBox.confirm('删除数据后无法恢复，您确认吗？','确认删除',{type:'warning'}).then(res => {
    request.delete('/film/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('删除成功')
        data.formVisible = false
      }else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}
const update = () => {
  request.put('/film/update', data.form).then(res => {
    if (res.code === '200') {
      load()
      data.formVisible = false
      ElMessage.success('更新成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const save = () => {
  data.form.id ? update() : add()
}

// 处理文件上传的钩子
const handleImgSuccess = (res) => {
  console.log('上传返回数据：', res)
  data.form.cover = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}
</script>
