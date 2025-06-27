<template>

    <v-card outlined>
        <v-card-title>
            TransformEbook
        </v-card-title>

        <v-card-text>
            <Number label="Id" v-model="value.id" :editMode="editMode"/>
            <String label="Summary" v-model="value.summary" :editMode="editMode"/>
            <String label="CoverUrl" v-model="value.coverUrl" :editMode="editMode"/>
            <String label="BookUrl" v-model="value.bookUrl" :editMode="editMode"/>
            <Date label="CreatedAt" v-model="value.createdAt" :editMode="editMode"/>
            <Date label="UpdatedAt" v-model="value.updatedAt" :editMode="editMode"/>
            <String label="ManuscriptTitle" v-model="value.manuscriptTitle" :editMode="editMode"/>
            <String label="ManuscriptContent" v-model="value.manuscriptContent" :editMode="editMode"/>
            <Number label="AuthorId" v-model="value.authorId" :editMode="editMode"/>
            <String label="AuthorName" v-model="value.authorName" :editMode="editMode"/>
            <String label="Introduction" v-model="value.introduction" :editMode="editMode"/>
            <String label="Category" v-model="value.category" :editMode="editMode"/>
            <Number label="Price" v-model="value.price" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="transformEbook"
            >
                TransformEbook
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'TransformEbookCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.id = 0;
        this.value.summary = '';
        this.value.coverUrl = '';
        this.value.bookUrl = '';
        this.value.createdAt = '2025-06-27';
        this.value.updatedAt = '2025-06-27';
        this.value.manuscriptTitle = '';
        this.value.manuscriptContent = '';
        this.value.authorId = 0;
        this.value.authorName = '';
        this.value.introduction = '';
        this.value.category = '';
        this.value.price = 0;
    },
    watch: {
    },
    methods: {
        transformEbook() {
            this.$emit('transformEbook', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

