<template>

    <div>
        <div class="detail-title">
        원고
        </div>
        <v-col>
            <String label="원고제목" v-model="value.manuscriptTitle" :editMode="editMode"/>
            <String label="원고내용" v-model="value.manuscriptContent" :editMode="editMode"/>
            <Number label="작가 ID" v-model="value.authorId" :editMode="editMode"/>
            <String label="작가이름" v-model="value.authorName" :editMode="editMode"/>
            <String label="작가설명" v-model="value.authorIntroduction" :editMode="editMode"/>
            <Date label="생성일" v-model="value.createdAt" :editMode="editMode"/>
            <Date label="수정일" v-model="value.updatedAt" :editMode="editMode"/>
        </v-col>

        <v-card-actions v-if="inList">
            <slot name="actions"></slot>
        </v-card-actions>
    </div>
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue'
import BasePicker from './base-ui/BasePicker.vue'

export default {
    name: '원고',
    mixins:[BaseEntity],
    components:{
        BasePicker
    },
    data: () => ({
        path: '원고',
    }),
    props: {
    },
    watch: {
        value(val){
            this.value = val;
            this.change();
        },
    },
    async created() {
        if (Array.isArray(this.modelValue)) {
            this.value = await Promise.all(this.modelValue.map(async (item) => {
                if (item && item.id !== undefined) {
                    return await this.repository.findById(item.id);
                }
                return item;
            }));
        } else {
            this.value = this.modelValue;
            if (this.value && this.value.id !== undefined) {
                this.value = await this.repository.findById(this.value.id);
            }
        }
    },
    methods: {
        pick(val){
            this.value = val;
            this.change();
        },
    }
}
</script>

