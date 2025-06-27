<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
                <v-btn style="margin-left: 5px;" @click="registerAuthorDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Author')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가등록
                </v-btn>
                <v-dialog v-model="registerAuthorDialog" width="500">
                    <RegisterAuthor
                        @closeDialog="registerAuthorDialog = false"
                        @registerAuthor="registerAuthor"
                    ></RegisterAuthor>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="approveAuthorDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가승인
                </v-btn>
                <v-dialog v-model="approveAuthorDialog" width="500">
                    <ApproveAuthor
                        @closeDialog="approveAuthorDialog = false"
                        @approveAuthor="approveAuthor"
                    ></ApproveAuthor>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="disapproveAuthorDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가비승인
                </v-btn>
                <v-dialog v-model="disapproveAuthorDialog" width="500">
                    <DisapproveAuthor
                        @closeDialog="disapproveAuthorDialog = false"
                        @disapproveAuthor="disapproveAuthor"
                    ></DisapproveAuthor>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="editAuthorDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Author')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가수정
                </v-btn>
                <v-dialog v-model="editAuthorDialog" width="500">
                    <EditAuthor
                        @closeDialog="editAuthorDialog = false"
                        @editAuthor="editAuthor"
                    ></EditAuthor>
                </v-dialog>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>작가email</th>
                        <th>작가이름</th>
                        <th>작가소개</th>
                        <th>승인여부</th>
                        <th>생성일</th>
                        <th>수정일</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="작가email">{{ val.email }}</td>
                            <td class="whitespace-nowrap" label="작가이름">{{ val.name }}</td>
                            <td class="whitespace-nowrap" label="작가소개">{{ val.introduction }}</td>
                            <td class="whitespace-nowrap" label="승인여부">{{ val.isApproved }}</td>
                            <td class="whitespace-nowrap" label="생성일">{{ val.createdAt }}</td>
                            <td class="whitespace-nowrap" label="수정일">{{ val.updatedAt }}</td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Author 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Author :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Author 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="작가email" v-model="selectedRow.email" :editMode="true"/>
                            <String label="작가이름" v-model="selectedRow.name" :editMode="true"/>
                            <String label="작가소개" v-model="selectedRow.introduction" :editMode="true"/>
                            <Boolean label="승인여부" v-model="selectedRow.isApproved" :editMode="true"/>
                            <Date label="생성일" v-model="selectedRow.createdAt" :editMode="true"/>
                            <Date label="수정일" v-model="selectedRow.updatedAt" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: 'authorGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'authors',
        registerAuthorDialog: false,
        approveAuthorDialog: false,
        disapproveAuthorDialog: false,
        editAuthorDialog: false,
    }),
    watch: {
    },
    methods:{
        async registerAuthor(params){
            try{
                var path = "registerAuthor".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','register author 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.registerAuthorDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async approveAuthor(params){
            try{
                var path = "approveAuthor".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','approve author 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.approveAuthorDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async disapproveAuthor(params){
            try{
                var path = "disapproveAuthor".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','disapprove author 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.disapproveAuthorDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async editAuthor(params){
            try{
                var path = "editAuthor".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','edit author 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.editAuthorDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>