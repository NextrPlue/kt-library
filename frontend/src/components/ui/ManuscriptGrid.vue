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
                <v-btn style="margin-left: 5px;" @click="registerManuscriptDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Author')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>원고 등록
                </v-btn>
                <v-dialog v-model="registerManuscriptDialog" width="500">
                    <RegisterManuscript
                        @closeDialog="registerManuscriptDialog = false"
                        @registerManuscript="registerManuscript"
                    ></RegisterManuscript>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="editManuscriptDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Author')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>원고 수정
                </v-btn>
                <v-dialog v-model="editManuscriptDialog" width="500">
                    <EditManuscript
                        @closeDialog="editManuscriptDialog = false"
                        @editManuscript="editManuscript"
                    ></EditManuscript>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="requestPublishingDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Author')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>출간 요청
                </v-btn>
                <v-dialog v-model="requestPublishingDialog" width="500">
                    <RequestPublishing
                        @closeDialog="requestPublishingDialog = false"
                        @requestPublishing="requestPublishing"
                    ></RequestPublishing>
                </v-dialog>
            </div>
            <ReadAuthor @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></ReadAuthor>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>원고제목</th>
                        <th>원고내용</th>
                        <th>작가 ID</th>
                        <th>작가이름</th>
                        <th>작가설명</th>
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
                            <td class="whitespace-nowrap" label="원고제목">{{ val.manuscriptTitle }}</td>
                            <td class="whitespace-nowrap" label="원고내용">{{ val.manuscriptContent }}</td>
                            <td class="whitespace-nowrap" label="작가 ID">{{ val.authorId }}</td>
                            <td class="whitespace-nowrap" label="작가이름">{{ val.authorName }}</td>
                            <td class="whitespace-nowrap" label="작가설명">{{ val.authorIntroduction }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Manuscript 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Manuscript :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Manuscript 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="원고제목" v-model="selectedRow.manuscriptTitle" :editMode="true"/>
                            <String label="원고내용" v-model="selectedRow.manuscriptContent" :editMode="true"/>
                            <Number label="작가 ID" v-model="selectedRow.authorId" :editMode="true"/>
                            <String label="작가이름" v-model="selectedRow.authorName" :editMode="true"/>
                            <String label="작가설명" v-model="selectedRow.authorIntroduction" :editMode="true"/>
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
    name: 'manuscriptGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'manuscripts',
        registerManuscriptDialog: false,
        editManuscriptDialog: false,
        requestPublishingDialog: false,
    }),
    watch: {
    },
    methods:{
        async registerManuscript(params){
            try{
                var path = "registerManuscript".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','register manuscript 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.registerManuscriptDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async editManuscript(params){
            try{
                var path = "editManuscript".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','edit manuscript 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.editManuscriptDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async requestPublishing(params){
            try{
                var path = "requestPublishing".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','requestPublishing 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.requestPublishingDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>