# Main Menu Loop
def main():
    while True:
        print("\n=== PYTHON STUDENT MANAGEMENT SYSTEM ===")
        print("1. Add Student")
        print("2. View All Students")
        print("3. Search Student")
        print("4. Update Student")
        print("5. Delete Student")
        print("6. Generate Summary Report")
        print("7. Exit")
        
        choice = input("Select an option (1-7): ")
        if choice == '1':
            add_student()
        elif choice == '2':
            view_students()
        elif choice == '3':
            search_student()
        elif choice == '4':
            update_student()
        elif choice == '5':
            delete_student()
        elif choice == '6':
            generate_report()
        elif choice == '7':
            print("Exiting program.")
            break
        else:
            print("Invalid selection. Try again.")

if __name__ == "__main__":
    main()