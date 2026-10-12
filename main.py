import os
import shutil

class FileManager:
    def init(self, base_dir="managed_files"):
        self.base_dir = base_dir
        if not os.path.exists(self.base_dir):
            os.makedirs(self.base_dir)

    def create_file(self, filename, content=""):
        try:
            if not filename or not filename.strip():
                raise ValueError("फाइल का नाम खाली नहीं हो सकता।")
            file_path = os.path.join(self.base_dir, filename.strip())
            with open(file_path, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"सफलता: फाइल '{filename}' बन गई है।")
        except Exception as e:
            print(f"त्रुटि: {e}")

    def read_file(self, filename):
        try:
            file_path = os.path.join(self.base_dir, filename.strip())
            if not os.path.exists(file_path):
                raise FileNotFoundError("फाइल मौजूद नहीं है।")
            print(f"\n--- {filename} की सामग्री ---")
            with open(file_path, 'r', encoding='utf-8') as f:
                print(f.read())
            print("-----------------------------")
        except Exception as e:
            print(f"त्रुटि: {e}")

    def list_files(self):
        files = os.listdir(self.base_dir)
        if not files:
            print("फोल्डर खाली है।")
        else:
            print("\n--- उपलब्ध फाइलें ---")
            for f in files:
                print(f"- {f}")
            print("----------------------")

    def delete_file(self, filename):
        try:
            file_path = os.path.join(self.base_dir, filename.strip())
            if not os.path.exists(file_path):
                raise FileNotFoundError("फाइल मौजूद नहीं है।")
            os.remove(file_path)
            print(f"सफलता: फाइल '{filename}' डिलीट कर दी गई है।")
        except Exception as e:
            print(f"त्रुटि: {e}")

    def organize_files(self):
        items = os.listdir(self.base_dir)
        for item in items:
            item_path = os.path.join(self.base_dir, item)
            if os.path.isfile(item_path):
                ext = item.split('.')[-1] if '.' in item else 'no_ext'
                ext_dir = os.path.join(self.base_dir, ext.upper() + "_files")
                if not os.path.exists(ext_dir):
                    os.makedirs(ext_dir)
                shutil.move(item_path, os.path.join(ext_dir, item))
        print("सफलता: सभी फाइलें एक्सटेंशन के अनुसार व्यवस्थित हो गई हैं।")

def main():
    fm = FileManager()
    while True:
        print("\n=== फाइल मैनेजमेंट यूटिलिटी ===")
        print("1. नई फाइल बनाएं")
        print("2. फाइल पढ़ें")
        print("3. सभी फाइलें देखें")
        print("4. फाइल डिलीट करें")
        print("5. फाइलों को व्यवस्थित करें")
        print("6. बाहर निकलें")
        
        choice = input("विकल्प चुनें (1-6): ").strip()

        if choice == '1':
            fname = input("फाइल का नाम (जैसे test.txt): ")
            content = input("कंटेंट लिखें: ")
            fm.create_file(fname, content)
        elif choice == '2':
            fname = input("पढ़ने के लिए फाइल का नाम: ")
            fm.read_file(fname)
        elif choice == '3':
            fm.list_files()
        elif choice == '4':
            fname = input("डिलीट करने के लिए फाइल का नाम: ")
            fm.delete_file(fname)
        elif choice == '5':
            fm.organize_files()
        elif choice == '6':
            print("प्रोग्राम बंद हो रहा है।")
            break
        else:
            print("अमान्य विकल्प, 1 से 6 के बीच चुनें।")

if __name__ == "__main__":
    main()