package reports;

public enum EnumBlockType
{
	NONE {
		public String title(){
			return "������������� ��� �������";
		}
	},
	DOCUMENTS {
		public String title(){
			return "������������";
		}
	},
	COMPLEXES {
		public String title(){
			return "���������";
		}
	},
	ASSEMBLIES {
		public String title(){
			return "��������� �������";
		}
	},
	DETAILS {
		public String title(){
			return "������";
		}
	},
	STANDARDS {
		public String title(){
			return "����������� �������";
		}
	},
	OTHERS {
		public String title(){
			return "������ �������";
		}
	},
	MATERIALS {
		public String title(){
			return "���������";
		}
	},
	KITS {
		public String title(){
			return "���������";
		}
	};
	
	public abstract String title();
}
