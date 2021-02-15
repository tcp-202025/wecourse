<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {

  data() {
    return {
      filterText: '',
      data2: [],  //返回所有分类数据
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  created() {
      this.getAllSubjectList()
  },
  watch: {  //在vue框架中用filter对el-tree树形控件过滤，定义watch 监听搜索框关键字的改变，实现数据双向绑定
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  methods: {
    
    //查询所有的课程分类
    getAllSubjectList() {
        subject.getSubjectList()
            .then(response => {
                this.data2 = response.data.list
            })
    },

    //首部的Input框检索功能
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  }
}
</script>