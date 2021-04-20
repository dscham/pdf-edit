import EditorJS from './node_modules/@editorjs/editorjs';
import Header from './node_modules/@editorjs/header';
import List from './node_modules/@editorjs/list';
import CheckList from './node_modules/@editorjs/checklist';
import Image from './node_modules/@editorjs/image';
import Delimiter from './node_modules/@editorjs/delimiter';
import Table from './node_modules/@editorjs/table';

export class EditorController {
    saveButton = document.querySelector('#save');
    editor = new EditorJS({
        holder: 'editorjs',
        tools: {
            header: {
                class: Header,
                inlineToolbar: true
            },
            list: {
                class: List,
                inlineToolbar: true
            },
            checklist: {
                class: CheckList,
                inlineToolbar: true
            },
            image: {
                class: Image,
                config: {
                    endpoints: {
                        byFile: 'api/image',
                        byUrl: 'api/image/by-url',
                    }
                },
                inlineToolbar: true,
            },
            delimiter: {
                class: Delimiter,
                inlineToolbar: true
            },
            table: {
                class: Table,
                inlineToolbar: true
            }
        }
    });


    constructor() {
        alert("Editor opened!");
        this.editor.isReady.then(() => {
            this.editor.focus();
            this.editor.toolbar.open();
            this.saveButton.addEventListener('click', this.saveDocument);
        });
    }

    saveDocument() {
        this.editor.save().then((document) => {
            console.log('EditorController - Saving Document.', document);
        });
    }
}

document.addEventListener("load", () => new EditorController());
