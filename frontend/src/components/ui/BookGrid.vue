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
                <v-btn style="margin-left: 5px;" @click="requestCoverDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>AI 커버 생성 요청
                </v-btn>
                <v-dialog v-model="requestCoverDialog" width="500">
                    <RequestCover
                        @closeDialog="requestCoverDialog = false"
                        @requestCover="requestCover"
                    ></RequestCover>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="transformEbookDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>전자책 변환 요청
                </v-btn>
                <v-dialog v-model="transformEbookDialog" width="500">
                    <TransformEbook
                        @closeDialog="transformEbookDialog = false"
                        @transformEbook="transformEbook"
                    ></TransformEbook>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="setCategoryDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>카테고리 설정 요청
                </v-btn>
                <v-dialog v-model="setCategoryDialog" width="500">
                    <SetCategory
                        @closeDialog="setCategoryDialog = false"
                        @setCategory="setCategory"
                    ></SetCategory>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="requestRegistrationDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>도서 등록 요청
                </v-btn>
                <v-dialog v-model="requestRegistrationDialog" width="500">
                    <RequestRegistration
                        @closeDialog="requestRegistrationDialog = false"
                        @requestRegistration="requestRegistration"
                    ></RequestRegistration>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="calculatePriceDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>가격 산정 요청
                </v-btn>
                <v-dialog v-model="calculatePriceDialog" width="500">
                    <CalculatePrice
                        @closeDialog="calculatePriceDialog = false"
                        @calculatePrice="calculatePrice"
                    ></CalculatePrice>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="generateSummaryDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>AI 요약 생성요청
                </v-btn>
                <v-dialog v-model="generateSummaryDialog" width="500">
                    <GenerateSummary
                        @closeDialog="generateSummaryDialog = false"
                        @generateSummary="generateSummary"
                    ></GenerateSummary>
                </v-dialog>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>요약</th>
                        <th>커버url</th>
                        <th>도서url</th>
                        <th>생성일</th>
                        <th>수정일</th>
                        <th>원고제목</th>
                        <th>원고내용</th>
                        <th>작가ID</th>
                        <th>작가이름</th>
                        <th>작가소개</th>
                        <th>책분류</th>
                        <th>책가격</th>
                        <th>Gen. AI</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="요약">{{ val.summary }}</td>
                            <td class="whitespace-nowrap" label="커버url">{{ val.coverUrl }}</td>
                            <td class="whitespace-nowrap" label="도서url">{{ val.bookUrl }}</td>
                            <td class="whitespace-nowrap" label="생성일">{{ val.createdAt }}</td>
                            <td class="whitespace-nowrap" label="수정일">{{ val.updatedAt }}</td>
                            <td class="whitespace-nowrap" label="원고제목">{{ val.manuscriptTitle }}</td>
                            <td class="whitespace-nowrap" label="원고내용">{{ val.manuscriptContent }}</td>
                            <td class="whitespace-nowrap" label="작가ID">{{ val.authorId }}</td>
                            <td class="whitespace-nowrap" label="작가이름">{{ val.authorName }}</td>
                            <td class="whitespace-nowrap" label="작가소개">{{ val.introduction }}</td>
                            <td class="whitespace-nowrap" label="책분류">{{ val.category }}</td>
                            <td class="whitespace-nowrap" label="책가격">{{ val.price }}</td>
                            <td class="whitespace-nowrap" label="Gen. AI">
                                <GenAiId :editMode="editMode" v-model="val.genAiId"></GenAiId>
                            </td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Book 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Book :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Book 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="요약" v-model="selectedRow.summary" :editMode="true"/>
                            <String label="커버url" v-model="selectedRow.coverUrl" :editMode="true"/>
                            <String label="도서url" v-model="selectedRow.bookUrl" :editMode="true"/>
                            <Date label="생성일" v-model="selectedRow.createdAt" :editMode="true"/>
                            <Date label="수정일" v-model="selectedRow.updatedAt" :editMode="true"/>
                            <String label="원고제목" v-model="selectedRow.manuscriptTitle" :editMode="true"/>
                            <String label="원고내용" v-model="selectedRow.manuscriptContent" :editMode="true"/>
                            <Number label="작가ID" v-model="selectedRow.authorId" :editMode="true"/>
                            <String label="작가이름" v-model="selectedRow.authorName" :editMode="true"/>
                            <String label="작가소개" v-model="selectedRow.introduction" :editMode="true"/>
                            <String label="책분류" v-model="selectedRow.category" :editMode="true"/>
                            <Number label="책가격" v-model="selectedRow.price" :editMode="true"/>
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
    name: 'bookGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'books',
        requestCoverDialog: false,
        transformEbookDialog: false,
        setCategoryDialog: false,
        requestRegistrationDialog: false,
        calculatePriceDialog: false,
        generateSummaryDialog: false,
    }),
    watch: {
    },
    methods:{
        async requestCover(params){
            try{
                var path = "requestCover".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','request cover 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.requestCoverDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async transformEbook(params){
            try{
                var path = "transformEbook".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','transform ebook 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.transformEbookDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async setCategory(params){
            try{
                var path = "setCategory".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','set category 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.setCategoryDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async requestRegistration(params){
            try{
                var path = "requestRegistration".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','request registration 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.requestRegistrationDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async calculatePrice(params){
            try{
                var path = "calculatePrice".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','calculate price 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.calculatePriceDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async generateSummary(params){
            try{
                var path = "generateSummary".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','generate summary 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.generateSummaryDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>